package com.hyfata.sonichub.settings;

public enum RepeatMode {
    OFF("꺼짐", "<:no_repeat:1131228812697403403>"),
    ALL("전체 반복", "<:repeat:1131227169742405725>"),
    SINGLE("단일 반복", "<:repeat_single:1131227171600482314>");

    private final String name, emoji;

    RepeatMode(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }
}
