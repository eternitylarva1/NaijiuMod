package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.PortraitHelper;
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

public class SkillizedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(SkillizedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    public SkillizedMod() {
        this.priority = -1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        card.target = AbstractCard.CardTarget.NONE;
        if (card.type != AbstractCard.CardType.SKILL) {
            card.type = AbstractCard.CardType.SKILL;
            PortraitHelper.setMaskedPortrait(card);
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                card.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
                isDone = true;
            }
        });
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPower(card) && card.baseMagicNumber > 0;
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new SkillizedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
