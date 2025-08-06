package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Defragment;
import com.megacrit.cardcrawl.cards.blue.Heatsinks;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.red.Combust;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.powers.BiasedPower;

public class BiasedMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BiasedMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 3.0F;
    private static final int LOSE_PERCENT = 33;
    private static final int TURNS = 3;
    private boolean modMagic;

    private int getLoss(AbstractCard card) {
        return (int)(Math.round(card.magicNumber * LOSE_PERCENT / 100.0));
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0))) {
            this.modMagic = true;
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
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic)
            return magic * MULTIPLIER;
        return magic;
    }

    private boolean powerExists(AbstractCard card) {
        try {
            Class.forName("com.megacrit.cardcrawl.powers." + card.getClass().getSimpleName() + "Power").getConstructor(AbstractCreature.class, int.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean watcherPowerExists(AbstractCard card) {
        try {
            Class.forName("com.megacrit.cardcrawl.powers.watcher." + card.getClass().getSimpleName() + "Power").getConstructor(AbstractCreature.class, int.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isInBlacklist(AbstractCard card) {
        return card instanceof Combust // 自燃
            || card instanceof WraithForm; // 幽魂形态
    }

    private boolean isInWatcherBlacklist(AbstractCard card) {
        return card instanceof LiveForever; // 永生不死
    }

    private boolean isInWhitelist(AbstractCard card) {
        return card instanceof Inflame // 燃烧
            || card instanceof Footwork // 灵动步法
            || card instanceof Defragment // 碎片整理
            || card instanceof WellLaidPlans // 计划妥当
            || card instanceof Caltrops // 铁蒺藜
            || card instanceof AThousandCuts // 凌迟
            || card instanceof Heatsinks // 散热片
            || card instanceof SadisticNature; // 残虐天性
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasMagic(card)
                && AugmentHelper.isPower(card)
                && ((powerExists(card) && !isInBlacklist(card)) || (watcherPowerExists(card) && !isInWatcherBlacklist(card)) || isInWhitelist(card));
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (powerExists(card) && !isInBlacklist(card)) {
            try {
                AbstractPower power = (AbstractPower) Class.forName("com.megacrit.cardcrawl.powers." + card.getClass().getSimpleName() + "Power").getConstructor(AbstractCreature.class, int.class).newInstance(AbstractDungeon.player, card.magicNumber);
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, power, getLoss(card), TURNS)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (watcherPowerExists(card) && !isInWatcherBlacklist(card)) {
            try {
                AbstractPower power = (AbstractPower) Class.forName("com.megacrit.cardcrawl.powers.watcher." + card.getClass().getSimpleName() + "Power").getConstructor(AbstractCreature.class, int.class).newInstance(AbstractDungeon.player, card.magicNumber);
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, power, getLoss(card), TURNS)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (card instanceof Inflame) { // StrengthPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof Footwork) { // DexterityPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof Defragment) { // FocusPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new FocusPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof WellLaidPlans) { // RetainCardPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new RetainCardPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof Caltrops) { // ThornsPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof AThousandCuts) { // ThousandCutsPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new ThousandCutsPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof Heatsinks) { // HeatsinkPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new HeatsinkPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
        } else if (card instanceof SadisticNature) { // SadisticPower
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BiasedPower(AbstractDungeon.player, new SadisticPower(AbstractDungeon.player, card.magicNumber), getLoss(card), TURNS)));
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], TURNS, LOSE_PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BiasedMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
