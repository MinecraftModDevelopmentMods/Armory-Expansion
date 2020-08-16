package org.softc.armoryexpansion.common.integration.aelib.integration;

import com.google.gson.*;
import org.apache.commons.io.input.*;
import org.softc.armoryexpansion.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.alloys.*;

import java.io.*;

public class JsonIntegration extends AbstractIntegration {
    private final String json;

    protected JsonIntegration(String modId, String root, String json) {
        super(modId, root);
        this.json = json;
    }

    protected void loadMaterialsFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        ArmorToolRangedMaterial[] jsonMaterials = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), ArmorToolRangedMaterial[].class);
        this.loadMaterials(jsonMaterials);
    }

    @Override
    protected void loadMaterialsFromSource() {
        InputStream stream =
                this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-materials.json");
        if (null != stream) {
            this.loadMaterialsFromJson(stream);
        }
    }

    protected void loadOreDictionaryEntriesFromJson(InputStream path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        IOreDictionary[] jsonMaterials = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), BasicOreDictionary[].class);
        this.loadOreDictionaryEntries(jsonMaterials);
    }

    @Override
    protected void loadOreDictionaryEntriesFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-oreDictEntries.json");
        if (null != stream) {
            this.loadOreDictionaryEntriesFromJson(stream);
        }
    }

    protected void loadAlloysFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        Alloy[] jsonAlloys = new Alloy[0];
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize())));
            jsonAlloys = gson.fromJson(reader, Alloy[].class);
            reader.close();
        } catch (IOException e) {
            this.logger.error("", e);
        }

        this.loadAlloys(jsonAlloys);
    }

    @Override
    protected void loadAlloysFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-alloys.json");
        if (null != stream) {
            this.loadAlloysFromJson(stream);
        }
    }

    protected void loadConfigFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        MaterialConfigOptions[] jsonConfig = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), MaterialConfigOptions[].class);
        this.loadConfig(jsonConfig);
    }

    @Override
    protected void loadConfigFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-config.json");
        if (null != stream) {
            this.loadConfigFromJson(stream);
        }
    }

    protected void loadTraitsFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        MaterialTraits[] jsonMaterials = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), MaterialTraits[].class);
        this.loadTraits(jsonMaterials);
    }

    @Override
    protected void loadTraitsFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-traits.json");
        if (null != stream) {
            this.loadTraitsFromJson(stream);
        }
    }
}
