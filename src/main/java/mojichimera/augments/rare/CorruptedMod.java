package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.EchoFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class CorruptedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(CorruptedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int ECHOES = 4;
    private static final int EFFECT = 2;
    private static final int STATUS = 1;

    @Override
    public void onInitialApplication(AbstractCard card) {
        EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) + ECHOES);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isEchoValid(card)
                && card.magicNumber > 0
                && AugmentHelper.isPlayable(card)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!card.purgeOnUse) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, EFFECT, false), EFFECT));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, EFFECT, false), EFFECT));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, EFFECT, false), EFFECT));
            addToBot(new MakeTempCardInDrawPileAction(new Dazed(), STATUS, true, true));
            addToBot(new MakeTempCardInDrawPileAction(new Slimed(), STATUS, true, true));
            addToBot(new MakeTempCardInDrawPileAction(new Wound(), STATUS, true, true));
            addToBot(new MakeTempCardInDrawPileAction(new Burn(), STATUS, true, true));
            addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), STATUS, true, true));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], ECHOES));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new CorruptedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
