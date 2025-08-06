package mojichimera.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mojichimera.augments.rare.StrangeMod;

public class MojiHelper {
    public static boolean isInCombat() {
        return AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static String removeExhaustInDescription(String rawDescription) {
        final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(StrangeMod.ID).EXTRA_TEXT;
        if (rawDescription.contains(CARD_TEXT[3])) {
            return rawDescription.replace(CARD_TEXT[3], CARD_TEXT[4]);
        }
        if (rawDescription.contains(CARD_TEXT[1])) {
            return rawDescription.replace(CARD_TEXT[1], CARD_TEXT[2]);
        }
        return rawDescription;
    }

    public static AbstractCard getLastCardPlayedThisTurn() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            return AbstractDungeon.actionManager.cardsPlayedThisTurn.get(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1);
        }
        return null;
    }
}
