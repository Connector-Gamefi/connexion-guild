package com.connexion.cps.common.domain.page;


/**
 * table data handler
 * 
 * 
 */
public class TableSupport
{
    /**
     * current page
     */
    public static final String CURRENT_PAGE = "currentPage";

    /**
     * page size
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * order by column
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * sort
     */
    public static final String IS_ASC = "isAsc";

    /**
     * reasonable
     */
    public static final String REASONABLE = "reasonable";

    /**
     * page domain
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(CURRENT_PAGE));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    /**
     * package page
     */
    public static PageDomain getPageDomain(Integer currentPage,Integer pageSize)
    {
        PageDomain pageDomain = new PageDomain();
//        pageDomain.setPageNum(ServletUtils.getParameterToInt(CURRENT_PAGE));
//        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setPageNum(currentPage);
        pageDomain.setPageSize(pageSize);
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

    public static PageDomain buildPageRequest(Integer currentPage,Integer pageSize)
    {
        return getPageDomain(currentPage, pageSize);
    }
}
