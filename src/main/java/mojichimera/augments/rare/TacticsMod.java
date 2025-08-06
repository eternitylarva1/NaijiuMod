package mojichimera.augments.rare;

import Naijiumod.action.ImmediateExhaustCardAction;
import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.OnManualDiscardSubscriber;

public class TacticsMod extends AbstractAugment implements OnManualDiscardSubscriber {
    public static final String ID = mojichimera.makeID(TacticsMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
    }

    @Override
    public void onManualDiscard(AbstractCard card) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                card.use(AbstractDungeon.player, null);
                isDone = true;
            }
        });
        addToBot(new ImmediateExhaustCardAction(card));
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && doesntOverride(card, "triggerOnManualDiscard", new Class[]{});
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AugmentRarity getModRarity() { return AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new TacticsMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
