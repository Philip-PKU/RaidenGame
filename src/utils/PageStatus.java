package utils;

import world.pages.*;

/**
 * An enum object for game page status.
 *
 * @author 鏉ㄨ姵婧� 钄¤緣瀹�
 */
public enum PageStatus {
    MAIN(new MainPage()),
    RANK_LIST_ONE(new RankListOnePage()),
    RANK_LIST_TWO(new RankListTwoPage()),
    HELP(new HelpPage()),
    MODE_CHOSE(new ModeChosePage()),
    PLAYER_CHOSE(new PlayerChosePage()),
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
