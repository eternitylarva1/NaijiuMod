package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class VanityMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(VanityMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        CardModifierManager.addModifier(card, new EtherealMod());
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        // do nothing
    }

    @Override
    public void onDrawn(AbstractCard card) {
        card.use(AbstractDungeon.player, null);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isReplayable(card)
//                && cardCheck(card, c -> notEthereal(c))
                && doesntOverride(card, "triggerWhenDrawn", new Class[]{});
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VanityMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
