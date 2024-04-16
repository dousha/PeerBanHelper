package com.ghostchu.peerbanhelper.gui;

import com.ghostchu.peerbanhelper.Main;
import com.ghostchu.peerbanhelper.text.Lang;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@Slf4j
public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JList<String> logsList;
    private JButton sendCommandBtn;
    @Nullable
    private TrayIcon trayIcon;

    public MainWindow(GuiManager guiManager) {
//        mainPanel.add(titleLabel);
//        mainPanel.add(logConsole);
//        mainPanel.add(sendCommandBtn);
        setJMenuBar(setupMenuBar());
        //sendCommandBtn.addActionListener(e -> guiManager.setColorTheme(FlatDarculaLaf.class));
        setTitle("PeerBanHelper Console (GUI) - v" + Main.getMeta().getVersion() + "(" + Main.getMeta().getCommitIdShort() + ")");
        setVisible(true);
        setSize(800, 600);
        setContentPane(mainPanel);
        setupSystemTray();
        //setupLoggerForward();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (trayIcon != null) {
                    setVisible(false);
                    trayIcon.displayMessage(Lang.GUI_TRAY_MESSAGE_CAPTION, Lang.GUI_TRAY_MESSAGE_DESCRIPTION, TrayIcon.MessageType.INFO);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }


    private void setupSystemTray() {
        if (SystemTray.isSupported()) {
            TrayIcon icon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/assets/icon-tray.png")));
            icon.setImageAutoSize(true);
            //创建弹出菜单
            PopupMenu menu = new PopupMenu();
            //添加一个用于退出的按钮
            MenuItem item = new MenuItem("Exit");
            item.addActionListener(e -> System.exit(0));
            menu.add(item);
            //添加弹出菜单到托盘图标
            icon.setPopupMenu(menu);
            SystemTray tray = SystemTray.getSystemTray();//获取系统托盘
            icon.addActionListener(e -> setVisible(true));
            try {
                tray.add(icon);//将托盘图表添加到系统托盘
                this.trayIcon = icon;
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private JMenuBar setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu aboutMenu = new JMenu(Lang.GUI_MENU_ABOUT);
        menuBar.add(generateWebUIMenu());
        menuBar.add(aboutMenu);
        this.add(menuBar, BorderLayout.NORTH);
        return menuBar;
    }

    private JMenu generateWebUIMenu() {
        JMenu webUIMenu = new JMenu(Lang.GUI_MENU_WEBUI);
        JMenuItem openWebUIMenuItem = new JMenuItem(Lang.GUI_MENU_WEBUI_OPEN);
        webUIMenu.add(openWebUIMenuItem);
        return webUIMenu;
    }

}
