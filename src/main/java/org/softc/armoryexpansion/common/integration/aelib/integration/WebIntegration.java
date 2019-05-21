package org.softc.armoryexpansion.common.integration.aelib.integration;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.common.web.ArmoryExpansionWebClient;
import org.softc.armoryexpansion.common.web.IWebClient;

import java.io.InputStream;

public class WebIntegration extends JsonIntegration {
    protected IWebClient webClient;

    public WebIntegration(String modid, String json) {
        super(modid, json);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.webClient = ArmoryExpansionWebClient.getInstance();
        super.preInit(event);
    }

    @Override
    protected void setMaterials(String path){
        if(ArmoryExpansion.useServersForJsons()){
            this.loadMaterialsFromWeb();
            this.logger.info("Done loading all materials from web servers");
        }
        else{
            super.setMaterials(this.configDir);
        }
    }

    @Override
    protected void setAlloys(String path){
        if(ArmoryExpansion.useServersForJsons()){
            this.loadAlloysFromWeb();
            this.logger.info("Done loading all alloys from web servers");
        }
        else {
            super.setAlloys(this.configDir);
        }
    }

    @Override
    protected void setConfig(String path){
        if(ArmoryExpansion.useServersForJsons()){
            this.loadConfigFromWeb();
            this.logger.info("Done loading all configs from web servers");
        }
        else {
            super.setConfig(this.configDir);
        }
    }

    @Override
    protected void loadMaterialsFromWeb() {
        for (String url: ArmoryExpansion.getWebServerList()) {
            try {
                InputStream stream = this.webClient.sendGet(url, this.modid + "-materials.json","armory-expansion");
                if(stream == null){
                    return;
                }
                this.loadMaterialsFromJson(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArmoryExpansion.config.save();
    }

    @Override
    protected void loadAlloysFromWeb() {
        for (String url: ArmoryExpansion.getWebServerList()) {
            try {
                InputStream stream = this.webClient.sendGet(url, this.modid + "-alloys.json","armory-expansion");
                if(stream == null){
                    return;
                }
                this.loadAlloysFromJson(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArmoryExpansion.config.save();
    }

    @Override
    protected void loadConfigFromWeb() {
        for (String url: ArmoryExpansion.getWebServerList()) {
            try {
                InputStream stream = this.webClient.sendGet(url, this.modid + "-config.json","armory-expansion");
                if(stream == null){
                    return;
                }
                this.loadAlloysFromJson(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArmoryExpansion.config.save();
    }

    @Override
    protected void saveMaterials(String path){
        if(!ArmoryExpansion.useServersForJsons()){
            super.saveMaterials(path);
        }
    }

    @Override
    protected void saveAlloys(String path){
        if(!ArmoryExpansion.useServersForJsons()){
            super.saveAlloys(path);
        }
    }

    @Override
    protected void saveConfig(String path){
        if(!ArmoryExpansion.useServersForJsons()){
            super.saveConfig(path);
        }
    }
}
