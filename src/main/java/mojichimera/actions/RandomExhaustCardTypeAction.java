package mojichimera.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RandomExhaustCardTypeAction extends AbstractGameAction {
    private ArrayList<CardGroup> cardGroups = new ArrayList<>();
    private ArrayList<AbstractCard> valid = new ArrayList<>();
    private int amount;

    public RandomExhaustCardTypeAction(AbstractCard.CardType[] types, CardGroup[] groups, int amount) {
        for (CardGroup _group : groups) {
            cardGroups.add(_group);
            for (AbstractCard card : _group.group) {
                for (AbstractCard.CardType type : types) {
                    if (card.type == type) {
                        valid.add(card);
                        break;
                    }
                }
            }
        }
        this.amount = amount;
    }

    public void update() {
        if (valid.isEmpty()) {
            this.isDone = true;
            return;
        }
        for (int i = 0; i < amount; i++) {
            AbstractCard card = valid.remove(AbstractDungeon.cardRandomRng.random(valid.size() - 1));
            for (CardGroup _group : cardGroups) {
                if (_group.group.contains(card)) {
                    addToTop(new ExhaustSpecificCardAction(card, _group));
                }
            }
            if (valid.isEmpty()) {
                break;
            }
        }
        this.isDone = true;
    }
}
