package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ExperimentalMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ExperimentalMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int STATUS = 3;
    private static final float MIN_MULTIPLIER = 1.0F;
    private static final float MAX_MULTIPLIER = 2.5F;
//    private static final float EXTRA_MULTIPLIER = 0.25F;
    private static final Random rng = new Random();
//    public static final SpireField<Integer> bonus = new SpireField<>(() -> 0);
    public static final SpireField<Integer> baseDamage = new SpireField<>(() -> 0);
    public static final SpireField<Integer> baseBlock = new SpireField<>(() -> 0);
    public static final SpireField<Integer> baseMagic = new SpireField<>(() -> 0);
    public static final SpireField<Integer> damageMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> blockMap = new SpireField<>(() -> 0);
    public static final SpireField<Integer> magicMap = new SpireField<>(() -> 0);

    public ExperimentalMod() {
        this.priority = 1000;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        updateValues(card);
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damageMap.get(card) > 0)
            return (float) damageMap.get(card) / baseDamage.get(card) * damage;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (blockMap.get(card) > 0)
            return (float) blockMap.get(card) / baseBlock.get(card) * block;
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (magicMap.get(card) > 0)
            return (float) magicMap.get(card) / baseMagic.get(card) * magic;
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

    private float getMultiplier(AbstractCard card) {
        if (!MojiHelper.isInCombat())
            return 1.0F;
//        return 1.0F + (bonus.get(card) * EXTRA_MULTIPLIER);
        return rng.random(MIN_MULTIPLIER, MAX_MULTIPLIER);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.hasVariable(card)
                && AugmentHelper.isNormal(card)
                && !AugmentHelper.overrideDBMMods(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
//                int _bonus = bonus.get(card);
                for (int i = 0; i < STATUS; i++) {
                    AbstractCard c = null;
                    int status = rng.random(0, 4);
                    switch (status) {
                        case 4:
                            c = new VoidCard();
//                            _bonus += 3;
                            break;
                        case 3:
                            c = new Slimed();
//                            _bonus += 3;
                            break;
                        case 2:
                            c = new Burn();
//                            _bonus += 2;
                            break;
                        case 1:
                            c = new Wound();
//                            _bonus += 2;
                            break;
                        default:
                            c = new Dazed();
//                            _bonus += 1;
                            break;
                    }

                    int pile = rng.random(0, 2);
                    switch (pile) {
                        case 2:
                            addToTop(new MakeTempCardInDiscardAction(c, 1));
                            break;
                        case 1:
                            addToTop(new MakeTempCardInHandAction(c, 1));
//                            if (c instanceof Burn) {
//                                _bonus += 1;
//                            }
                            break;
                        default:
                            addToTop(new MakeTempCardInDrawPileAction(c, 1, true, true));
//                            _bonus += 1;
                            break;
                    }
                }
//                bonus.set(card, _bonus);
                isDone = true;
            }
        });

//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                bonus.set(card, 0);
//                isDone = true;
//            }
//        });

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                updateValues(card);
                isDone = true;
            }
        });
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        updateValues(card);
        card.initializeDescription();
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String tmp = insertAfterText(rawDescription, String.format(CARD_TEXT[0], STATUS));
        if (!MojiHelper.isInCombat()) {
            char c = (Settings.language.toString().equalsIgnoreCase("zhs")) ? '~' : '-';
            tmp = tmp.replace("!D!", String.format("%d%c%d", (int)Math.ceil(baseDamage.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseDamage.get(card) * MAX_MULTIPLIER)));
            tmp = tmp.replace("!B!", String.format("%d%c%d", (int)Math.ceil(baseBlock.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseBlock.get(card) * MAX_MULTIPLIER)));
            tmp = tmp.replace("!M!", String.format("%d%c%d", (int)Math.ceil(baseMagic.get(card) * MIN_MULTIPLIER), c, (int)Math.ceil(baseMagic.get(card) * MAX_MULTIPLIER)));
        }
        return tmp;
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ExperimentalMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
