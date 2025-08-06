package mojichimera.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.mojichimera;

import static Naijiumod.cardModifier.AbstractAugment.insertAfterText;


public class PromiseMod extends AbstractCardModifier {
    public static final String ID = mojichimera.makeID(PromiseMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private final int cost;
    private static final int DRAW = 1;

    public PromiseMod(int baseCost) {
        this.cost = baseCost;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost = cost;
        card.costForTurn = card.cost;
    }

    @Override
    public void onExhausted(AbstractCard card) {
        addToBot(new DrawCardAction(DRAW));
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], DRAW));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PromiseMod(cost);
    }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
