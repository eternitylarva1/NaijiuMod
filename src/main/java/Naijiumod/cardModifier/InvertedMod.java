/*     */ package Naijiumod.cardModifier;
/*     */ 


/*     */ import Naijiumod.helpers.ModHelper;
import Naijiumod.patchs.ActionReplacementPatches;
import Naijiumod.utils.PortraitHelper;
import basemod.abstracts.AbstractCardModifier;
/*     */ import com.evacipated.cardcrawl.modthespire.Loader;
/*     */ import com.megacrit.cardcrawl.actions.common.DamageAction;
/*     */ import com.megacrit.cardcrawl.actions.common.GainBlockAction;
/*     */ import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
/*     */ import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
/*     */ import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
/*     */ import com.megacrit.cardcrawl.actions.unique.RitualDaggerAction;
/*     */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.cards.purple.Brilliance;
/*     */ import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javassist.ClassPool;
/*     */ import javassist.CtClass;
/*     */ import javassist.CtMethod;
/*     */ import javassist.bytecode.CodeAttribute;
/*     */ import javassist.bytecode.CodeIterator;
/*     */ import javassist.bytecode.MethodInfo;
/*     */ import javassist.expr.ExprEditor;
/*     */ import javassist.expr.MethodCall;
/*     */ import javassist.expr.NewExpr;
/*     */ 
/*     */ public class InvertedMod
/*     */   extends AbstractAugment
/*     */ {
/*  34 */   public static final String ID = ModHelper.makePath(InvertedMod.class.getSimpleName());
/*  35 */   public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(ID)).TEXT;
/*  36 */   public static final String[] CARD_TEXT = (CardCrawlGame.languagePack.getUIString(ID)).EXTRA_TEXT;
/*  37 */   public static final ArrayList<Class> bannedCards = new ArrayList<>(Arrays.asList(new Class[] { PerfectedStrike.class, Brilliance.class }));
/*     */   
/*     */   private boolean toBlock;
/*     */   
/*     */   public void onInitialApplication(AbstractCard card) {
/*  42 */     if (card.rawDescription.contains(CARD_TEXT[1]) && card.type != AbstractCard.CardType.SKILL) {
/*  43 */       card.type = AbstractCard.CardType.SKILL;
/*  44 */       PortraitHelper.setMaskedPortrait(card);
/*  45 */       card.baseBlock = card.baseDamage;
/*  46 */       this.toBlock = true;
/*  47 */     } else if (card.rawDescription.contains(CARD_TEXT[0]) && card.type != AbstractCard.CardType.ATTACK) {
/*  48 */       card.type = AbstractCard.CardType.ATTACK;
/*  49 */       PortraitHelper.setMaskedPortrait(card);
/*  50 */       card.baseDamage = card.baseBlock;
/*  51 */       card.target = AbstractCard.CardTarget.ENEMY;
/*  52 */       this.toBlock = false;
/*     */     } 
/*  54 */     ActionReplacementPatches.InvertedFields.isInverted.set(card, Boolean.valueOf(true));
/*  55 */     ActionReplacementPatches.InvertedFields.toBlock.set(card, Boolean.valueOf(this.toBlock));
/*     */     
/*  57 */     final int[] hits = { 0 };
/*  58 */     ClassPool pool = Loader.getClassPool();
/*     */     try {
/*  60 */       CtClass ctClass = pool.getCtClass(card.getClass().getName());
/*  61 */       ctClass.defrost();
/*  62 */       MethodInfo info = ctClass.getClassFile2().getMethod("use");
/*  63 */       CodeAttribute ca = info.getCodeAttribute();
/*  64 */       CodeIterator it = ca.iterator();
/*  65 */       while (it.hasNext()) {
/*  66 */         int index = it.next();
/*  67 */         int op = it.byteAt(index);
/*     */ 
/*     */         
/*  70 */         if (op == 44) {
/*  71 */           hits[0] = hits[0] + 1;
/*     */         }
/*     */       } 
/*  74 */       CtMethod ctUse = ctClass.getDeclaredMethod("use");
/*  75 */       ctUse.instrument(new ExprEditor()
/*     */           {
/*     */             public void edit(NewExpr e) {
/*  78 */               if (e.getClassName().equals(DamageAction.class.getName()))
/*     */               {
/*  80 */                 hits[0] = hits[0] - 1;
/*     */               }
/*     */             }
/*     */ 
/*     */             
/*     */             public void edit(MethodCall m) {
/*     */               try {
/*  87 */                 CtMethod check = m.getMethod();
/*  88 */                 check.instrument(new ExprEditor()
/*     */                     {
/*     */                       public void edit(NewExpr e) {
/*  91 */                         if (e.getClassName().equals(DamageAction.class.getName())) {
/*  92 */                           hits[0] = hits[0] - 1;
/*     */                         }
/*     */                       }
/*     */                     });
/*  96 */               } catch (Exception exception) {}
/*     */             }
/*     */           });
/*     */     }
/* 100 */     catch (Exception exception) {}
/* 101 */     if (this.toBlock) {
/* 102 */       if (hits[0] > 0) {
/* 103 */         card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
/*     */       } else {
/* 105 */         card.target = AbstractCard.CardTarget.SELF;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpgradeCheck(AbstractCard card) {
/* 112 */     if (this.toBlock) {
/* 113 */       card.baseBlock = card.baseDamage;
/* 114 */       card.upgradedBlock = card.upgradedDamage;
/*     */     } else {
/* 116 */       card.baseDamage = card.baseBlock;
/* 117 */       card.upgradedDamage = card.upgradedBlock;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validCard(AbstractCard card) {
/* 123 */     boolean damageText = cardCheck(card, c -> c.rawDescription.contains(CARD_TEXT[1]));
/* 124 */     boolean blockText = cardCheck(card, c -> c.rawDescription.contains(CARD_TEXT[0]));
/* 125 */     if (!damageText && !blockText) {
/* 126 */       return false;
/*     */     }
/* 128 */     boolean hasDamage = usesAction(card, DamageAction.class);
/* 129 */     boolean hasBlock = usesAction(card, GainBlockAction.class);
/* 130 */     if (hasDamage && hasBlock) {
/* 131 */       return false;
/*     */     }
/* 133 */     if (!hasDamage && !hasBlock) {
/* 134 */       return false;
/*     */     }
/* 136 */     return ((((damageText && hasDamage) ? 1 : 0) ^ ((blockText && hasBlock) ? 1 : 0)) != 0 && noShenanigans(card) && bannedCards.stream().noneMatch(c -> c.equals(card.getClass())) && 
/* 137 */       cardCheck(card, c -> customCheck(AbstractAugment::usesVanillaTargeting)) && 
/* 138 */       !usesAction(card, ModifyBlockAction.class) && !usesAction(card, ModifyDamageAction.class) && !usesAction(card, RitualDaggerAction.class) && !usesAction(card, IncreaseMiscAction.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 143 */     return TEXT[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 148 */     return TEXT[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAugmentDescription() {
/* 153 */     return TEXT[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public String modifyDescription(String rawDescription, AbstractCard card) {
/* 158 */     if (rawDescription.contains(CARD_TEXT[1])) {
/* 159 */       return rawDescription.replace(CARD_TEXT[1], CARD_TEXT[0]);
/*     */     }
/* 161 */     if (rawDescription.contains(CARD_TEXT[0])) {
/* 162 */       return rawDescription.replace(CARD_TEXT[0], CARD_TEXT[1]);
/*     */     }
/* 164 */     return rawDescription + " NL ???";
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractAugment.AugmentRarity getModRarity() {
/* 169 */     return AbstractAugment.AugmentRarity.RARE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractCardModifier makeCopy() {
/* 174 */     return (AbstractCardModifier)new InvertedMod();
/*     */   }
/*     */ 
/*     */   
/*     */   public String identifier(AbstractCard card) {
/* 179 */     return ID;
/*     */   }
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\杀戮尖塔 mod\CardAugments.jar!\CardAugments\cardmods\rare\InvertedMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */