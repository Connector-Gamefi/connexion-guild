package com.connexion.cps.controller.common;

import com.connexion.cps.common.domain.AjaxResult;
import com.connexion.cps.controller.common.req.TokenListReqData;
import com.connexion.cps.controller.game.response.TreasuryListVO;
import com.connexion.cps.exception.InvalidParamException;
import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * common req
 *
 * 
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private IFtContractService ftContractService;

    @Autowired
    private INftContractService nftContractService;

    @GetMapping("/tokenList")
    public AjaxResult tokenList(@Validated TokenListReqData reqData) {
        if (TokenTypeEnum.NFT.getValue().equals(reqData.getType())) {
            return AjaxResult.success(nftContractService.getErc721Contract(reqData.getAppId()));
        } else if (TokenTypeEnum.FT.getValue().equals(reqData.getType())) {
            return AjaxResult.success(ftContractService.getFtErc20Contract(reqData.getAppId()));
        } else {
            throw new InvalidParamException("type error:" + reqData.getType());
        }
    }

    @GetMapping("/treasuryList")
    public AjaxResult treasuryList(@RequestParam(required = true) Integer appId) {
        List<TFtContractEntity> ftContractEntityList = this.ftContractService.getFtErc20Contract(appId);
        List<TreasuryListVO> treasuryListVOList = Lists.newArrayList();
        ftContractEntityList.stream().map(ft -> {
            TreasuryListVO treasuryListVO = new TreasuryListVO();
            treasuryListVO.setGameAssetName(ft.getTokenSymbol());
            treasuryListVO.setContractAddress(ft.getContractAddress());
            treasuryListVOList.add(treasuryListVO);
            return ft;
        }).collect(Collectors.toList());

        List<TNftContractEntity> nftContractEntityList = this.nftContractService.getErc721Contract(appId);
        nftContractEntityList.stream().map(nft -> {
            TreasuryListVO treasuryListVO = new TreasuryListVO();
            treasuryListVO.setGameAssetName(nft.getGameAssetName());
            treasuryListVO.setContractAddress(nft.getContractAddress());
            treasuryListVOList.add(treasuryListVO);
            return nft;
        }).collect(Collectors.toList());
        return AjaxResult.success(treasuryListVOList);
    }

    @GetMapping("/userAssetsTypeList")
    public AjaxResult userAssetsTypeList() {
        return AjaxResult.success(UserAssetsTypeEnum.list());
    }
}
