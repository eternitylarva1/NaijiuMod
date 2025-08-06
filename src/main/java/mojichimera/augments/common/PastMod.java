package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;

import Naijiumod.cardModifier.PreviewedMod;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import Naijiumod.utils.PortraitHelper;
import Naijiumod.utils.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.cardmods.Value50UpMod;
import mojichimera.mojichimera;
import mojichimera.powers.NextTurnStartPlayPower;

public class PastMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PastMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private boolean inherentHack = true;
    private boolean modMagic;
    private static final float MULTIPLIER = 1.50F;
    private static final int TURN = 1;
    private static final int COPY = 1;

    public PastMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.inherentHack = true;
        AbstractCard preview = card.makeStatEquivalentCopy();
        this.inherentHack = false;
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new Value50UpMod());
        CardModifierManager.addModifier(preview, (AbstractCardModifier)new PreviewedMod());
        MultiCardPreview.add(card, new AbstractCard[] { preview });
        card.target = AbstractCard.CardTarget.SELF;
        if (preview.type == AbstractCard.CardType.POWER) {
            CardModifierManager.addModifier(card, new ExhaustMod());
        }
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        if (card.type != AbstractCard.CardType.SKILL) {
            card.type = AbstractCard.CardType.SKILL;
            PortraitHelper.setMaskedPortrait(card);
        }
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
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
                    Wiz.applyToSelf(new NextTurnStartPlayPower(AbstractDungeon.player, copy, TURN, COPY));
//                    Wiz.applyToSelf(new BombPower(AbstractDungeon.player, TURN, COPY, copy));
                }
                this.isDone = true;
            }
        });
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
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * MULTIPLIER;
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesVariable(card, 2)
                && AugmentHelper.isPlayable(card)
                && AugmentHelper.isNormal(card)
                && noShenanigans(card)
                && !AugmentHelper.hasMultiPreviewMod(card, PastMod.ID);
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
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PastMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public boolean isInherent(AbstractCard card) {
        return this.inherentHack;
    }
}
