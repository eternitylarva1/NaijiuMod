package mojichimera.powers;

import Naijiumod.utils.FormatHelper;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import mojichimera.mojichimera;

public class RushdownProtocolPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = mojichimera.makeID(RushdownProtocolPower.class.getSimpleName());

    private static final PowerStrings TEXT = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private final AbstractCard card;

    public RushdownProtocolPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.name = TEXT.NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.card = card;
        this.isTurnBased = false;
        this.loadRegion("rushdown");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT.DESCRIPTIONS[0], this.amount, FormatHelper.prefixWords(this.card.name, "#y"));

        if (this.description.contains("copyreplace")) {
            this.description = this.description.replace("copyreplace", (amount == 1) ? "copy" : "copies");
        }
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!oldStance.ID.equals(newStance.ID) && newStance.ID.equals("Wrath")) {
            this.flash();
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    for (int i = 0; i < RushdownProtocolPower.this.amount; ++i) {
                        AbstractCard tmp = RushdownProtocolPower.this.card.makeSameInstanceOf();
                        AbstractDungeon.player.limbo.addToBottom(tmp);
                        tmp.current_x = RushdownProtocolPower.this.card.current_x;
                        tmp.current_y = RushdownProtocolPower.this.card.current_y;
                        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                        tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                        tmp.purgeOnUse = true;
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, true, RushdownProtocolPower.this.card.energyOnUse, true, true), true);
                    }

                    this.isDone = true;
                }
            });
        }
    }
}
