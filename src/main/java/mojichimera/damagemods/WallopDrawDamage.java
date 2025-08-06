package mojichimera.damagemods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WallopDrawDamage extends AbstractDamageModifier {
    private int draw;
    public WallopDrawDamage(int draw) {
        this.draw = draw;
    }

    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            addToBot(new DrawCardAction(AbstractDungeon.player, this.draw));
        }

    }

    public boolean isInherent() {
        return true;
    }

    public AbstractDamageModifier makeCopy() {
        return new WallopDrawDamage(this.draw);
    }
}
