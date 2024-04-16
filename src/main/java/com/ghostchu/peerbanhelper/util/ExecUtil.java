package com.ghostchu.peerbanhelper.util;

import com.ghostchu.peerbanhelper.text.Lang;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
@Slf4j
public class ExecUtil {
    public static void invokeCommand(String command, Map<String, String> env) {
        StringTokenizer st = new StringTokenizer(command);
        String[] cmdarray = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++)
            cmdarray[i] = st.nextToken();
        try {
            ProcessBuilder builder = new ProcessBuilder(cmdarray)
                    .inheritIO();
            Map<String, String> liveEnv = builder.environment();
            liveEnv.putAll(env);
            Process p = builder.start();
            p.waitFor(5, TimeUnit.SECONDS);
            if (p.isAlive()) {
                log.info(Lang.BANLIST_INVOKER_COMMAND_EXEC_TIMEOUT, command);
            }
            p.onExit().thenAccept(process -> {
                if (process.exitValue() != 0) {
                    log.warn(Lang.BANLIST_INVOKER_COMMAND_EXEC_FAILED, command, process.exitValue());
                }
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
