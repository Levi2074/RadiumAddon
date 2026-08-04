package com.example.radiumaddon.gui;

import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.gui.themes.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class CustomCustomScreen extends WindowScreen {
    public CustomCustomScreen(GuiTheme theme) {
        super(theme, "Radium Custom Dashboard");
    }

    @Override
    public void initWidgets() {
        WTable table = add(theme.table()).expandX().widget();

        for (Module module : Modules.get().getGroup("Radium Tools")) {
            table.add(theme.label(module.title)).expandX();
            WCheckbox checkbox = table.add(theme.checkbox(module.isActive())).widget();
            checkbox.action = () -> {
                module.toggle();
            };
            table.row();
        }
    }
}
