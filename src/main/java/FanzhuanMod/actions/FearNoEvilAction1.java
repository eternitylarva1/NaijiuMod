package FanzhuanMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;

public class FearNoEvilAction1 extends AbstractGameAction {
    private AbstractMonster m;
    private DamageInfo info;

    public FearNoEvilAction1(AbstractMonster m, DamageInfo info) {
        this.m = m;
        this.info = info;
    }

    public void update() {
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            this.addToTop(new ChangeStanceAction(WrathStance.STANCE_ID));
        }

        this.addToTop(new DamageAction(this.m, this.info, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.isDone = true;
    }
}
