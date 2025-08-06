package mojichimera.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnMoveToDiscardSubscriber {
    void onMoveToDiscard(AbstractCard card);
}
