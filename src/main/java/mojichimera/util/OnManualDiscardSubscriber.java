package mojichimera.util;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnManualDiscardSubscriber {
    void onManualDiscard(AbstractCard card);
}
