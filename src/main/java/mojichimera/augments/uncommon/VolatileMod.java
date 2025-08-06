package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class VolatileMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(VolatileMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
//    private static final int MIN_ECHOES = -1;
//    private static final int MAX_ECHOES = 2;
//    public static final SpireField<Integer> nextPlayEchoes = new SpireField<>(() -> -999);
//    public static final SpireField<Integer> debt = new SpireField<>(() -> 0);
    private static final int MIN_EFFECT = 0;
    private static final int MAX_EFFECT = 3;

    @Override
    public void onInitialApplication(AbstractCard card) {
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card, true)
                && AugmentHelper.isPlayable(card)
                && AugmentHelper.isAttackOrSkill(card);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                if (!card.purgeOnUse) {
//                    if (nextPlayEchoes.get(card) != -999) {
//                        EchoFieldPatches.EchoFields.echo.set(card, (Integer) EchoFieldPatches.EchoFields.echo.get(card) - nextPlayEchoes.get(card));
//                        nextPlayEchoes.set(card, -999);
//                    }
//                }
//                this.isDone = true;
//            }
//        });
        int effect = AbstractDungeon.cardRandomRng.random(MIN_EFFECT, MAX_EFFECT);
        if (card.exhaust && effect == 0) {
            effect = 1;
        }
        for (int i = 0; i < effect; i++) {
            card.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
        }
    }

//    @Override
//    public void onUpdate(AbstractCard card) {
//        if (CardCrawlGame.isInARun() && AbstractDungeon.player.hand.contains(card)) {
//            if (nextPlayEchoes.get(card) == -999) {
//                int echo = AbstractDungeon.cardRandomRng.random(MIN_ECHOES, MAX_ECHOES);
//                int result = EchoFieldPatches.EchoFields.echo.get(card) + echo;
//                if (result < 0) {
//                    debt.set(card, debt.get(card) - result);
//                    echo -= result;
//                    result = 0;
//                }
//                if (result > 0 && debt.get(card) > 0) {
//                    int pay = Math.min(debt.get(card), result);
//                    echo -= pay;
//                    result -= pay;
//                    debt.set(card, debt.get(card) - pay);
//                }
//                EchoFieldPatches.EchoFields.echo.set(card, result);
//                nextPlayEchoes.set(card, echo);
//            }
//        }
//    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], MIN_EFFECT, MAX_EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new VolatileMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
