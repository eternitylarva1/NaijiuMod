package mojichimera.damagemods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WallopSelfHPLossDamage extends AbstractDamageModifier {
    private int hpLoss;
    public WallopSelfHPLossDamage(int hpLoss) {
        this.hpLoss = hpLoss;
    }

    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.hpLoss));
        }

    }

    public boolean isInherent() {
        return true;
    }

    public AbstractDamageModifier makeCopy() {
        return new WallopSelfHPLossDamage(this.hpLoss);
    }
}
