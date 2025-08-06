package mojichimera.augments.special;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.SweepingBeam;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class OverchargedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(OverchargedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 2.3333334F;
    private static final int COST = 1;
    private static final int PERCENT = 233;
    public static final SpireField<Integer> playedTimes = new SpireField<>(() -> 0);
    private boolean modMagic;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return (float) (damage * Math.pow(MULTIPLIER, playedTimes.get(card)));
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return (float) (block * Math.pow(MULTIPLIER, playedTimes.get(card)));
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return (float) (magic * Math.pow(MULTIPLIER, playedTimes.get(card)));
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card)
                && AugmentHelper.isReplayable(card)
                && card.cost >= 0;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!(card instanceof SweepingBeam)) {
            addToTop(new SFXAction("ATTACK_DEFECT_BEAM"));
            addToTop(new VFXAction(AbstractDungeon.player, new SweepingBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal), 0.4F));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!card.purgeOnUse) {
                    playedTimes.set(card, playedTimes.get(card) + 1);
                    for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
                        c.updateCost(COST);
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT, COST));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.SPECIAL; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new OverchargedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
