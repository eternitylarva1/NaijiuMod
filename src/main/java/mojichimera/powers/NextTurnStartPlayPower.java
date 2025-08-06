package mojichimera.powers;

import Naijiumod.utils.FormatHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;
import mojichimera.mojichimera;

import java.util.ArrayList;

public class NextTurnStartPlayPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(NextTurnStartPlayPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

//    private static final PowerStrings TEXT = new PowerStrings();
    private final AbstractCard card;
    private final int cards;
    private float flashTimer = -1.0F;
    private final ArrayList<AbstractGameEffect> array;

//    static {
//        if (Settings.language.toString().toLowerCase().equals("zhs")) {
//            TEXT.NAME = "准备";
//            TEXT.DESCRIPTIONS = new String[] {"#b%1$s 回合后，在你的回合开始时，打出 #b%2$s 张 %3$s。",
//                    "在下个回合开始时，打出 #b%1$s 张 %2$s。"};
//        } else {
//            TEXT.NAME = "Prepared ";
//            TEXT.DESCRIPTIONS = new String[] {"In #b%1$s turns, play #b%2$s copies of %3$s at the start of your turn.",
//                    "At the start of next turn, play #b%1$s copies of %2$s."};
//        }
//    }

    public NextTurnStartPlayPower(AbstractCreature owner, AbstractCard card, int amount, int cards) {
        this.name = TEXT.NAME + card.name;
        this.ID = POWER_ID;
        this.amount = amount;
        this.cards = cards;
        this.owner = owner;
        this.card = card.makeStatEquivalentCopy();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("master_reality");
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
                this.addToBot(new AbstractGameAction() {
                    public void update() {
                        AbstractCard tmp = NextTurnStartPlayPower.this.card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = NextTurnStartPlayPower.this.card.current_x;
                        tmp.current_y = NextTurnStartPlayPower.this.card.current_y;
                        tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                        tmp.purgeOnUse = true;
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, NextTurnStartPlayPower.this.card.energyOnUse, true, true));

                        this.isDone = true;
                    }
                });
            }
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount == 1) {
            this.flashTimer = 2.0F;
        }
    }
}
