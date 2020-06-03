package main.utils;

import main.ui.pages.*;

/**
 * An enum object for game page status.
 *
 * @author 杨芳源 蔡辉宇
 */
public enum PageStatus {
    MAIN(new MainPage()),
    RANK_LIST_ONE(new RankListOnePage()),
    RANK_LIST_TWO(new RankListTwoPage()),
    HELP(new HelpPage()),
    MODE_CHOOSE(new ModeChoosePage()),
    PLAYER_CHOOSE(new PlayerChoosePage()),
    GAMING(new GamingPage()),
    VICTORY(new VictoryPage()),
    END(new EndPage()),
    CLOSE();

    Page page = null;

    PageStatus(Page page) {
        this.page = page;
    }

    PageStatus() {}

    public Page getPage() {
        return page;
    }
}
