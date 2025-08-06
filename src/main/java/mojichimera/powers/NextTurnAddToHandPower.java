package mojichimera.powers;

import Naijiumod.utils.FormatHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;
import mojichimera.mojichimera;

import java.util.ArrayList;

public class NextTurnAddToHandPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(NextTurnAddToHandPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private final AbstractCard card;
    private final int cards;
    private float flashTimer = -1.0F;
    private final ArrayList<AbstractGameEffect> array;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "量产";
//            TEXT.DESCRIPTIONS = new String[] {"#b%1$s 回合后，将 #b%2$s 张 %3$s 加入手牌。",
//                    "在下个回合开始时，将 #b%1$s 张 %2$s 加入手牌。"};
//        } else {
//            TEXT.NAME = " Mass Production";
//            TEXT.DESCRIPTIONS = new String[] {"In #b%1$s turns, add #b%2$s copies of %3$s to your hand.",
//                    "At the start of next turn, add #b%1$s copies of %2$s to your hand."};
//        }
//    }

    public NextTurnAddToHandPower(AbstractCreature owner, AbstractCard card, int amount, int cards) {
        this.name = TEXT.NAME + card.name;
        this.ID = POWER_ID;
        this.amount = amount;
        this.cards = cards;
        this.owner = owner;
        this.card = card.makeStatEquivalentCopy();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("book");
        this.updateDescription();
        this.array = (ArrayList)ReflectionHacks.getPrivateInherited(this, NextTurnStartPlayPower.class, "effect");
    }

    public void update(int slot) {
        super.update(slot);
        if (this.flashTimer != -1.0F) {
            this.flashTimer += Gdx.graphics.getDeltaTime();
            if (this.flashTimer > 2.0F) {
                this.array.add(new SilentGainPowerEffect(this));
                this.flashTimer = 0.0F;
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = String.format(TEXT.DESCRIPTIONS[1], this.cards, FormatHelper.prefixWords(this.card.name, "#y"));
        } else {
            this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, this.cards, FormatHelper.prefixWords(this.card.name, "#y"));
        }

        if (this.description.contains("copyreplace")) {
            this.description = this.description.replace("copyreplace", (cards == 1) ? "copy" : "copies");
        }
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                this.flash();
                for (int i = 0; i < this.cards; ++i) {
                    AbstractCard copy = this.card.makeStatEquivalentCopy();
//                    if (this.awaken) {
//                        CardModifierManager.removeModifiersById(copy, UnawakenedMod.ID, true);
//                        CardModifierManager.addModifier(copy, new AwakenedMod());
//                    }
                    this.addToBot(new MakeTempCardInHandAction(copy));
                }
            }
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount == 1) {
            this.flashTimer = 2.0F;
        }
    }
}
