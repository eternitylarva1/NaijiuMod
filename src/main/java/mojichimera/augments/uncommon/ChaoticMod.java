package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ChaoticMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ChaoticMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MIN_MULTIPLIER = 0.5F;
    private static final float MAX_MULTIPLIER = 2.0F;
    private static final Random rng = new Random();
    public static final SpireField<Integer> baseDamage = new SpireField<>(() -> 0);
    public static final SpireField<Integer> baseBlock = new SpireField<>(() -> 0);
    public static final SpireField<Integer> baseMagic = new SpireField<>(() -> 0);
    public static final SpireField<Integer> damageMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> blockMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> magicMap = new SpireField<>(() -> 0);

    public ChaoticMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        updateValues(card);
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damageMap.get(card) > 0)
            return damageMap.get(card);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (blockMap.get(card) > 0)
            return blockMap.get(card);
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (magicMap.get(card) > 0)
            return magicMap.get(card);
        return magic;
    }

    private void updateValues(AbstractCard card) {
        try {
            if (card.baseDamage > 0) {
                baseDamage.set(card, card.baseDamage);
                damageMap.set(card, (int) Math.ceil(card.baseDamage * (rng.random(MIN_MULTIPLIER, MAX_MULTIPLIER))));
            }
            if (card.baseBlock > 0) {
                baseBlock.set(card, card.baseBlock);
                blockMap.set(card, (int) Math.ceil(card.baseBlock * (rng.random(MIN_MULTIPLIER, MAX_MULTIPLIER))));
            }
            if (card.baseMagicNumber > 0) {
                baseMagic.set(card, card.baseMagicNumber);
                magicMap.set(card, (int) Math.ceil(card.baseMagicNumber * (rng.random(MIN_MULTIPLIER, MAX_MULTIPLIER))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card, true)
                && AugmentHelper.isNormal(card)
                && !AugmentHelper.overrideDBMMods(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        updateValues(card);
        card.initializeDescription();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (!MojiHelper.isInCombat()) {
            char c = (Settings.language.toString().equalsIgnoreCase("zhs")) ? '~' : '-';
            String tmp = rawDescription.replace("!D!", String.format("%d%c%d", (int)Math.ceil(baseDamage.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseDamage.get(card) * MAX_MULTIPLIER)));
            tmp = tmp.replace("!B!", String.format("%d%c%d", (int)Math.ceil(baseBlock.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseBlock.get(card) * MAX_MULTIPLIER)));
            tmp = tmp.replace("!M!", String.format("%d%c%d", (int)Math.ceil(baseMagic.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseMagic.get(card) * MAX_MULTIPLIER)));
            return tmp;
        }
        return rawDescription;
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ChaoticMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
