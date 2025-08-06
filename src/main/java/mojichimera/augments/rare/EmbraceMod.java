package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.PreviewedMod;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.BlockPerNonAttackAction;
import com.megacrit.cardcrawl.actions.unique.ExhaustAllNonAttackAction;
import com.megacrit.cardcrawl.actions.unique.FiendFireAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.cardmods.DamageBlock67OffMod;
import mojichimera.mojichimera;
import mojichimera.powers.DarkProtocolPower;
import mojichimera.util.MojiHelper;

public class EmbraceMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(EmbraceMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private boolean inherentHack = true;
    private static final int EFFECT = 1;
    private static final float MULTIPLIER = 0.3333334F;

    public EmbraceMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new DamageBlock67OffMod());
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        card.target = AbstractCard.CardTarget.NONE;
        if (card.type != AbstractCard.CardType.POWER) {
            card.type = AbstractCard.CardType.POWER;
            PortraitHelper.setMaskedPortrait(card);
        }
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
    public void onUpgradeCheck(AbstractCard card) {
        for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
            if (CardModifierManager.hasModifier(c, PreviewedMod.ID)) {
                c.upgrade();
                c.initializeDescription();
            }
        }
        card.initializeDescription();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard preview = null;
                for (AbstractCard c : MultiCardPreview.multiCardPreview.get(card)) {
                    if (CardModifierManager.hasModifier(c, PreviewedMod.ID))
                        preview = c;
                }
                if (preview != null) {
                    AbstractCard copy = preview.makeStatEquivalentCopy();
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarkProtocolPower(AbstractDungeon.player, copy, EFFECT), EFFECT));
                }
                this.isDone = true;
            }
        });
    }

    private boolean isInBlacklist(AbstractCard card) {
        return usesAction(card, ExhaustAction.class)
                || usesAction(card, ExhaustAllNonAttackAction.class)
                || usesAction(card, BlockPerNonAttackAction.class)
                || usesAction(card, FiendFireAction.class);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesDamageOrBlock(card, 3)
                && card.cost >= 0
                && !AugmentHelper.hasMultiPreviewMod(card, EmbraceMod.ID)
                && !isInBlacklist(card)
                && AugmentHelper.isPowerizeValid(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertBeforeText(MojiHelper.removeExhaustInDescription(rawDescription), CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new EmbraceMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
