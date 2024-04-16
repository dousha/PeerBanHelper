package com.ghostchu.peerbanhelper.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.ghostchu.peerbanhelper.Main;
import com.ghostchu.peerbanhelper.text.Lang;
import com.jthemedetecor.OsThemeDetector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GuiManager {
    private MainWindow mainWindow;
    public GuiManager(String[] args){
        if(isSupportGUI()){
           setupUI(args);
        }
    }

    private void setupUI(String[] args) {
        File noGuiConsoleNotify = new File(Main.getDataDirectory(), ".noguiconsolenofify");
        if (System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            if(System.console() != null) {
                if (!noGuiConsoleNotify.exists()) {
                    JOptionPane.showMessageDialog(null, Lang.WINDOWS_GUI_CONSOLE_ALERT);
                    try {
                        noGuiConsoleNotify.createNewFile();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
        OsThemeDetector detector = OsThemeDetector.getDetector();
        onColorThemeChanged();
        detector.registerListener(isDark -> onColorThemeChanged());
        mainWindow = new MainWindow(this);
    }

    public boolean isSupportGUI(){
        return Desktop.isDesktopSupported();
    }

    public void onColorThemeChanged(){
         OsThemeDetector detector = OsThemeDetector.getDetector();
         boolean isDarkThemeUsed = detector.isDark();
         if(isDarkThemeUsed){
             setColorTheme(FlatDarculaLaf.class);
         }else{
             setColorTheme(FlatIntelliJLaf.class);
         }
    }


    public void setColorTheme(Class<?> clazz){
        if(clazz.getName().equals( UIManager.getLookAndFeel().getClass().getName() ) )
            return;
        try {
            UIManager.setLookAndFeel( clazz.getName() );
        } catch( Exception ex ) {

        }
        FlatLaf.updateUI();
    }
}
