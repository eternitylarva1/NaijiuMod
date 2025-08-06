package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.augments.special.ShareHelperMod;
import mojichimera.mojichimera;

public class ShareMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ShareMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 0.6666667F;

    @Override
    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new ExhaustMod());
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * MULTIPLIER;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * MULTIPLIER;
        return block;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesDamageOrBlock(card, 2)
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                boolean hasDamage = card.baseDamage > 0;
                boolean hasBlock = card.baseBlock > 0;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c != card) {
                        if (hasDamage && c.baseDamage > 0) {
                            if (CardModifierManager.hasModifier(c, ShareHelperMod.ID)) {
                                ShareHelperMod.sharedBlock.set(c, ShareHelperMod.sharedBlock.get(c) + card.damage);
                            } else {
                                CardModifierManager.addModifier(c, new ShareHelperMod());
                                ShareHelperMod.sharedBlock.set(c, card.damage);
                            }
                        }
                        if (hasBlock && c.baseBlock > 0) {
                            if (CardModifierManager.hasModifier(c, ShareHelperMod.ID)) {
                                ShareHelperMod.sharedBlock.set(c, ShareHelperMod.sharedBlock.get(c) + card.block);
                            } else {
                                CardModifierManager.addModifier(c, new ShareHelperMod());
                                ShareHelperMod.sharedBlock.set(c, card.block);
                            }
                        }
                    }
                }
                this.isDone = true;
            }
        });
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ShareMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
