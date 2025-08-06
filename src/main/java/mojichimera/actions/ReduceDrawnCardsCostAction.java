package mojichimera.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@Deprecated
public class ReduceDrawnCardsCostAction extends AbstractGameAction {
    private final int CARDS;
    private final int EFFECT;
//    private final AbstractCard.CardType TYPE;
    private static final int MAX_HAND_SIZE = 10;

    public ReduceDrawnCardsCostAction(int MagicNumber, int currentHandSize, int effect, AbstractCard.CardType... types) {
        this.actionType = ActionType.CARD_MANIPULATION;
        CARDS = Math.min(MagicNumber, MAX_HAND_SIZE + 1 - currentHandSize);
        EFFECT = effect;
//        TYPE = types.length > 0 ? types[0] : null;
    }

    public void update() {
        if (CARDS <= 0 || AbstractDungeon.player.hand.isEmpty()) {
            this.isDone = true;
            return;
        }
        for (int i = 0; i < CARDS; i++) {
            int idx = AbstractDungeon.player.hand.size() - 1 - i;
            if (idx < 0) {
                break;
            }
//            if (TYPE != null && AbstractDungeon.player.hand.getNCardFromTop(i).type != TYPE) {
//                break;
//            }
            AbstractDungeon.player.hand.getNCardFromTop(i).updateCost(-EFFECT);
        }
        this.isDone = true;
    }
}
