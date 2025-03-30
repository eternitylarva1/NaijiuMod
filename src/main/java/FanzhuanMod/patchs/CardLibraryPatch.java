package FanzhuanMod.patchs;

import FanzhuanMod.cardModifier.CalmModifier;
import FanzhuanMod.cardModifier.RandomStanceModifier;
import FanzhuanMod.cardModifier.StartModifier;
import FanzhuanMod.utils.Invoker;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;

import java.util.ArrayList;

@SpirePatch(clz = CardLibraryScreen.class, method = "initialize")
public class CardLibraryPatch {

    @SpirePostfixPatch

    public static void Postfix(CardLibraryScreen screen, CardGroup ___redCards, CardGroup ___greenCards, CardGroup ___blueCards, CardGroup ___colorlessCards,CardGroup ___purpleCards) {
        ArrayList<CardGroup> cards = new ArrayList<CardGroup>();
        cards.add(___redCards);
        cards.add(___greenCards);
        cards.add(___blueCards);
        cards.add(___colorlessCards);
        cards.add(___purpleCards);
        cards.forEach(cardGroup -> {
            cardGroup.group.forEach(OnCardGeneratedPatches::addModifier);
        });


    }

}
