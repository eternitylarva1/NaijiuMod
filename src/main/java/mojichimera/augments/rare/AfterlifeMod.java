package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.NextTurnStartPlayPower;

public class AfterlifeMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(AfterlifeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int TURN = 1;
    private static final int COPY = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new EtherealMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return (card.cost >= 0 && card.cost <= 2)
                && AugmentHelper.isNormal(card)
                && AugmentHelper.isEtherealValid(card);
    }

    @Override
    public void onExhausted(AbstractCard card) {
        card.flash(Color.RED.cpy());
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            card.use(AbstractDungeon.player, AbstractDungeon.getRandomMonster());
        } else {
            Wiz.applyToSelf(new NextTurnStartPlayPower(AbstractDungeon.player, card, TURN, COPY));
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0]));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new AfterlifeMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
