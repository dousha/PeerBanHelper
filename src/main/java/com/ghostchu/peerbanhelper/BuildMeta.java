package com.ghostchu.peerbanhelper;

import lombok.Data;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;
@Data
public class BuildMeta {
    private String version = "unknown";
    private boolean isNativeImage;
    private String commitIdShort;

    public BuildMeta() {
    }

    public void loadBuildMeta(YamlConfiguration configuration) {
        this.version = configuration.getString("maven.version");
        this.commitIdShort = configuration.getString("git.commit.id.abbrev");
        this.isNativeImage = configuration.getString("env.native-image","false").equalsIgnoreCase("true");
    }

}
