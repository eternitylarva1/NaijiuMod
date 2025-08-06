package mojichimera.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import java.util.Iterator;

public class ReduceAllDebuffsAction extends AbstractGameAction {
    private AbstractCreature c;

    public ReduceAllDebuffsAction(AbstractCreature c, int amount) {
        this.c = c;
        this.duration = 0.5F;
        this.amount = amount;
    }

    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
        } else {
            Iterator var1 = this.c.powers.iterator();
            AbstractPower p;

            while(true) {
                while(true) {
                    do {
                        if (!var1.hasNext()) {
                            this.isDone = true;
                            return;
                        }

                        p = (AbstractPower)var1.next();
                    } while(p.type != PowerType.DEBUFF);

                    if (p.amount > 0) {
                        this.addToTop(new ReducePowerAction(this.c, this.c, p.ID, this.amount));
                    } else if (p.amount < 0 && Math.abs(p.amount) <= this.amount) {
                        this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p));
                    } else {
                        AbstractPower finalP = p;
                        this.addToTop(new AbstractGameAction() {
                            public void update() {
                                finalP.stackPower(ReduceAllDebuffsAction.this.amount);
                                finalP.updateDescription();
                                AbstractDungeon.onModifyPower();
                                this.isDone = true;
                            }
                        });
                    }
                }
            }
        }
    }
}
