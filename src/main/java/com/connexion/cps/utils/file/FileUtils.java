package com.connexion.cps.utils.file;

import com.connexion.cps.config.sys.connexionConfig;
import com.connexion.cps.utils.uuid.IdUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * file handler util
 *
 * 
 * 
 */
public class FileUtils
{
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * Output the byte array of the specified file
     * 
     * @param filePath
     * @param os
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            IOUtils.close(os);
            IOUtils.close(fis);
        }
    }

    public static MultipartFile getMultipartFileByFile(String file){
        File fi = new File(file);
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , fi.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        return new CommonsMultipartFile(item);
    }

    public static boolean directoryExists(File f){
        return f.exists() && f.isDirectory();
    }

    public static boolean fileExists(String filePath){
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }

    public static String joinPath(String...paths){

        if(paths == null || paths.length == 0) {
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<paths.length; i++){

            if(i > 0 && i <= paths.length -1 ){
                sb.append("/");
            }

            String p = paths[i];
            sb.append(p);
        }

        String path = formatRightPath(sb.toString());

        return path;
    }

    public static String formatRightPath(String path){

        if(StringUtils.isEmpty(path)) {
            return path;
        }

        path = path.replaceAll("\\\\", "/");

        while(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }

        while(path.startsWith("/") && path.length() > 1 && path.charAt(1) == '/'){
            path = path.substring(0, path.length()-1);
        }
        return path;
    }

    public static void saveFileFromStream(InputStream inputStream, String targetFile){
        try{
            makeParentDirs(targetFile);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, new File(targetFile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeParentDirs(String file){
        File f = new File(file);
        File fd = f.getParentFile();
        if(!fd.exists()){
            fd.mkdirs();
        }
    }

    /**
     * write data to file
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String writeImportBytes(byte[] data) throws IOException
    {
        return writeBytes(data, connexionConfig.getImportPath());
    }

    /**
     * Write data to file
     *
     * @param data
     * @param uploadDir
     * @return dest file
     * @throws IOException
     */
    public static String writeBytes(byte[] data, String uploadDir) throws IOException
    {
        FileOutputStream fos = null;
        String pathName = "";
        try
        {
            String extension = getFileExtendName(data);
            pathName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
            File file = FileUploadUtils.getAbsoluteFile(uploadDir, pathName);
            fos = new FileOutputStream(file);
            fos.write(data);
        }
        finally
        {
            IOUtils.close(fos);
        }
        return FileUploadUtils.getPathFileName(uploadDir, pathName);
    }

    /**
     * del file
     * 
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteFile(File file) {
        try {
            if(file == null || !file.exists()) {
                return false;
            }
            org.apache.commons.io.FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * File name verification
     * 
     * @param filename
     * @return true ｜ false
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * Check if the file is downloadable
     * 
     * @param resource
     * @return true｜false
     */
    public static boolean checkAllowDownload(String resource)
    {
        if (StringUtils.contains(resource, ".."))
        {
            return false;
        }

        if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource)))
        {
            return true;
        }

        return false;
    }

    /**
     * Download file name re-encoding
     * 
     * @param request
     * @param fileName
     * @return
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // firefox
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // other
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * Download file name re-encoding
     *
     * @param response
     * @param realFileName
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    /**
     * Percent encoding tool method
     *
     * @param s
     * @return
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * Get image suffix
     * 
     * @param photoByte
     * @return
     */
    public static String getFileExtendName(byte[] photoByte)
    {
        String strFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97))
        {
            strFileExtendName = "gif";
        }
        else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70))
        {
            strFileExtendName = "jpg";
        }
        else if ((photoByte[0] == 66) && (photoByte[1] == 77))
        {
            strFileExtendName = "bmp";
        }
        else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71))
        {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }

    /**
     * Get name
     * 
     * @param fileName
     * @return filename
     */
    public static String getName(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    public static String formatPath(String path){

        if(StringUtils.isEmpty(path)) return path;

        path = path.replaceAll("\\\\", "/");

        while(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }
        return path;
    }

    public static void cloneFile(String sourceFile, String targetFile){
        try {
            org.apache.commons.io.FileUtils.copyFile(new File(sourceFile), new File(targetFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the file name in the path, including suffix
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath){
        return FilenameUtils.getName(filePath);
    }
}
