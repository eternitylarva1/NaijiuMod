package mojichimera.augments;


import Naijiumod.cardModifier.*;
import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.Feed;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import mojichimera.augments.common.*;
import mojichimera.augments.rare.*;
import mojichimera.augments.uncommon.*;
import mojichimera.mojichimera;

import static Naijiumod.cardModifier.AbstractAugment.*;
import static mojichimera.mojichimera.makeID;

public class AugmentHelper {
    public static void register() {

        // Bans
        // Peaceful

        // EmbraceMod
//        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Hemokinesis); // 御血术
//        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Wish); // 许愿
//        CardAugmentsMod.registerCustomBan(EmbraceMod.ID, c -> c instanceof Conclude); // 结末

        // StormMod
//        CardAugmentsMod.registerCustomBan(StormMod.ID, c -> c instanceof Hemokinesis); // 御血术
//        CardAugmentsMod.registerCustomBan(StormMod.ID, c -> c instanceof Wish); // 许愿
//        CardAugmentsMod.registerCustomBan(StormMod.ID, c -> c instanceof Conclude); // 结末
//        CardAugmentsMod.registerCustomBan(StormMod.ID, c -> c instanceof Offering); // 祭品
//        CardAugmentsMod.registerCustomBan(StormMod.ID, c -> c instanceof Blasphemy); // 渎神

        // CounterMod
//        CardAugmentsMod.registerCustomBan(CounterMod.ID, c -> c instanceof Hemokinesis); // 御血术
//        CardAugmentsMod.registerCustomBan(CounterMod.ID, c -> c instanceof Wish); // 许愿
//        CardAugmentsMod.registerCustomBan(CounterMod.ID, c -> c instanceof Conclude); // 结末

        // FireBreathingMod
//        CardAugmentsMod.registerCustomBan(FireBreathingMod.ID, c -> c instanceof Hemokinesis); // 御血术
//        CardAugmentsMod.registerCustomBan(FireBreathingMod.ID, c -> c instanceof Wish); // 许愿
//        CardAugmentsMod.registerCustomBan(FireBreathingMod.ID, c -> c instanceof Conclude); // 结末
//        CardAugmentsMod.registerCustomBan(FireBreathingMod.ID, c -> c instanceof Offering); // 祭品
//        CardAugmentsMod.registerCustomBan(FireBreathingMod.ID, c -> c instanceof Blasphemy); // 渎神

        // SadisticMod
//        CardAugmentsMod.registerCustomBan(SadisticMod.ID, c -> c instanceof Hemokinesis); // 御血术
//        CardAugmentsMod.registerCustomBan(SadisticMod.ID, c -> c instanceof Wish); // 许愿
//        CardAugmentsMod.registerCustomBan(SadisticMod.ID, c -> c instanceof Conclude); // 结末
//        CardAugmentsMod.registerCustomBan(SadisticMod.ID, c -> c instanceof Offering); // 祭品
//        CardAugmentsMod.registerCustomBan(SadisticMod.ID, c -> c instanceof Blasphemy); // 渎神
    }

    public static boolean hasMultiPreviewMod(AbstractCard card, String ...modid) {
        String[] mods = new String[] {
                BundledMod.ID,
                InfiniteMod.ID,
                PastMod.ID,
//                SkillizedMod.ID,
                UnawakenedMod.ID,
                HeatsinksMod.ID,
                EmbraceMod.ID,
                RushdownMod.ID,
                CounterMod.ID,
                FireBreathingMod.ID,
                SadisticMod.ID,
                GluttonousMod.ID,
                FutureMod.ID,
                DelayedMod.ID
        };

//        for (String id : modid) {
//            for (int i = 0; i < mods.length; i++) {
//                if (mods[i] != null && mods[i].equals(id)) {
//                    mods[i] = null;
//                    break;
//                }
//            }
//        }

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }

    public static boolean hasChangeTypeMod(AbstractCard card) {
        String[] mods = new String[] {
                InvertedMod.ID,
                BundledMod.ID,

                InfiniteMod.ID,
                SkillizedMod.ID,
                HeatsinksMod.ID,
                EmbraceMod.ID,
                RushdownMod.ID,
                CounterMod.ID,
                FireBreathingMod.ID,
                SadisticMod.ID
        };

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }

    public static boolean overrideDBMMods(AbstractCard card) {
        String[] mods = new String[] {
                ChaoticMod.ID,
                ExperimentalMod.ID,
        };

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }

    public static boolean hasDamage(AbstractCard card) {
        return card.baseDamage > 0;
    }

    public static boolean reachesDamage(AbstractCard card, int threshold) {
        return card.baseDamage >= threshold;
    }

    public static boolean hasBlock(AbstractCard card) {
        return card.baseBlock > 0;
    }

    public static boolean reachesBlock(AbstractCard card, int threshold) {
        return card.baseBlock >= threshold;
    }

    public static boolean hasMagic(AbstractCard card) {
        return cardCheck(card, c -> c.baseMagicNumber > 0 && doesntDowngradeMagic());
    }

    public static boolean reachesMagic(AbstractCard card, int threshold) {
        return cardCheck(card, c -> c.baseMagicNumber >= threshold && doesntDowngradeMagic());
    }

    public static boolean hasDamageOrBlock(AbstractCard card) {
        return card.baseDamage > 0 || card.baseBlock > 0;
    }

    public static boolean reachesDamageOrBlock(AbstractCard card, int threshold) {
        return card.baseDamage >= threshold || card.baseBlock >= threshold;
    }

    public static boolean hasVariable(AbstractCard card, boolean ...doesntCareDowngrade) {
        if (doesntCareDowngrade.length > 0 && doesntCareDowngrade[0])
            return card.baseMagicNumber > 0 || card.baseDamage > 0 || card.baseBlock > 0;
        return hasDamage(card) || hasBlock(card) || hasMagic(card);
    }

    public static boolean reachesVariable(AbstractCard card, int threshold) {
        return reachesDamage(card, threshold) || reachesBlock(card, threshold) || reachesMagic(card, threshold);
    }

    public static boolean isPlayable(AbstractCard card) {
        return card.cost != -2;
    }

    public static boolean hasStaticCost(AbstractCard card, int ...cost) {
        if (cost.length > 0)
            return cardCheck(card, c -> c.cost >= cost[0] && doesntUpgradeCost());
        return cardCheck(card, c -> c.cost >= 0 && doesntUpgradeCost());
    }
    public static boolean isReplayable(AbstractCard card) {
        return cardCheck(card, c -> isAttackOrSkill(c) && notExhaust(c) && isPlayable(c));
    }

    public static boolean isEtherealValid(AbstractCard card) {
        return cardCheck(card, c -> notExhaust(c) && notRetain(c));
    }

    public static boolean isRetainValid(AbstractCard card) {
        return cardCheck(card, c -> notEthereal(c));
    }

    public static boolean isAttack(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK;
    }

    public static boolean isSkill(AbstractCard card) {
        return card.type == AbstractCard.CardType.SKILL;
    }

    public static boolean isPower(AbstractCard card) {
        return card.type == AbstractCard.CardType.POWER;
    }

    public static boolean isAttackOrSkill(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL;
    }

    public static boolean isNormal(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.POWER;
    }

    public static boolean isPowerizeValid(AbstractCard card) {
        return !usesAction(card, LoseHPAction.class)
                && !usesAction(card, PressEndTurnButtonAction.class)
                && !usesAction(card, ChooseOneAction.class)
                && !usesClass(card, EndTurnDeathPower.class)
//                && doesntOverride(card, "canUse", new Class[]{AbstractPlayer.class, AbstractMonster.class});
                && noShenanigans(card)
                && !card.rarity.equals(AbstractCard.CardRarity.BASIC);
    }

    public static boolean isEchoValid(AbstractCard card) {
        return !usesAction(card, PressEndTurnButtonAction.class)
                && (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL
                || (card.type == AbstractCard.CardType.POWER && card.magicNumber > 0));
    }

    public static boolean isPriority(AbstractCard card) {
        String[] mods = {
                AbsoluteMod.ID,
                PreemptiveMod.ID,
                ReservedMod.ID
        };

        for (String id : mods) {
            if (CardModifierManager.hasModifier(card, id))
                return true;
        }

        return false;
    }
}
