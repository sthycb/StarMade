/*   1:    */package it.unimi.dsi.fastutil.chars;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */import java.util.Iterator;
/*   5:    */import java.util.ListIterator;
/*   6:    */import java.util.NoSuchElementException;
/*   7:    */
/*  54:    */public class CharIterators
/*  55:    */{
/*  56:    */  public static class EmptyIterator
/*  57:    */    extends AbstractCharListIterator
/*  58:    */    implements Serializable, Cloneable
/*  59:    */  {
/*  60:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  61:    */    
/*  62: 62 */    public boolean hasNext() { return false; }
/*  63: 63 */    public boolean hasPrevious() { return false; }
/*  64: 64 */    public char nextChar() { throw new NoSuchElementException(); }
/*  65: 65 */    public char previousChar() { throw new NoSuchElementException(); }
/*  66: 66 */    public int nextIndex() { return 0; }
/*  67: 67 */    public int previousIndex() { return -1; }
/*  68: 68 */    public int skip(int n) { return 0; }
/*  69: 69 */    public int back(int n) { return 0; }
/*  70: 70 */    public Object clone() { return CharIterators.EMPTY_ITERATOR; }
/*  71: 71 */    private Object readResolve() { return CharIterators.EMPTY_ITERATOR; }
/*  72:    */  }
/*  73:    */  
/*  79: 79 */  public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*  80:    */  
/*  81:    */  private static class SingletonIterator extends AbstractCharListIterator {
/*  82:    */    private final char element;
/*  83:    */    private int curr;
/*  84:    */    
/*  85: 85 */    public SingletonIterator(char element) { this.element = element; }
/*  86:    */    
/*  87: 87 */    public boolean hasNext() { return this.curr == 0; }
/*  88: 88 */    public boolean hasPrevious() { return this.curr == 1; }
/*  89:    */    
/*  90: 90 */    public char nextChar() { if (!hasNext()) throw new NoSuchElementException();
/*  91: 91 */      this.curr = 1;
/*  92: 92 */      return this.element;
/*  93:    */    }
/*  94:    */    
/*  95: 95 */    public char previousChar() { if (!hasPrevious()) throw new NoSuchElementException();
/*  96: 96 */      this.curr = 0;
/*  97: 97 */      return this.element;
/*  98:    */    }
/*  99:    */    
/* 100:100 */    public int nextIndex() { return this.curr; }
/* 101:    */    
/* 102:    */    public int previousIndex() {
/* 103:103 */      return this.curr - 1;
/* 104:    */    }
/* 105:    */  }
/* 106:    */  
/* 110:    */  public static CharListIterator singleton(char element)
/* 111:    */  {
/* 112:112 */    return new SingletonIterator(element);
/* 113:    */  }
/* 114:    */  
/* 115:    */  private static class ArrayIterator extends AbstractCharListIterator {
/* 116:    */    private final char[] array;
/* 117:    */    private final int offset;
/* 118:    */    private final int length;
/* 119:    */    private int curr;
/* 120:    */    
/* 121:    */    public ArrayIterator(char[] array, int offset, int length) {
/* 122:122 */      this.array = array;
/* 123:123 */      this.offset = offset;
/* 124:124 */      this.length = length;
/* 125:    */    }
/* 126:    */    
/* 127:127 */    public boolean hasNext() { return this.curr < this.length; }
/* 128:128 */    public boolean hasPrevious() { return this.curr > 0; }
/* 129:    */    
/* 130:    */    public char nextChar() {
/* 131:131 */      if (!hasNext()) throw new NoSuchElementException();
/* 132:132 */      return this.array[(this.offset + this.curr++)];
/* 133:    */    }
/* 134:    */    
/* 135:    */    public char previousChar() {
/* 136:136 */      if (!hasPrevious()) throw new NoSuchElementException();
/* 137:137 */      return this.array[(this.offset + --this.curr)];
/* 138:    */    }
/* 139:    */    
/* 140:    */    public int skip(int n) {
/* 141:141 */      if (n <= this.length - this.curr) {
/* 142:142 */        this.curr += n;
/* 143:143 */        return n;
/* 144:    */      }
/* 145:145 */      n = this.length - this.curr;
/* 146:146 */      this.curr = this.length;
/* 147:147 */      return n;
/* 148:    */    }
/* 149:    */    
/* 150:    */    public int back(int n) {
/* 151:151 */      if (n <= this.curr) {
/* 152:152 */        this.curr -= n;
/* 153:153 */        return n;
/* 154:    */      }
/* 155:155 */      n = this.curr;
/* 156:156 */      this.curr = 0;
/* 157:157 */      return n;
/* 158:    */    }
/* 159:    */    
/* 160:    */    public int nextIndex() {
/* 161:161 */      return this.curr;
/* 162:    */    }
/* 163:    */    
/* 164:    */    public int previousIndex() {
/* 165:165 */      return this.curr - 1;
/* 166:    */    }
/* 167:    */  }
/* 168:    */  
/* 179:    */  public static CharListIterator wrap(char[] array, int offset, int length)
/* 180:    */  {
/* 181:181 */    CharArrays.ensureOffsetLength(array, offset, length);
/* 182:182 */    return new ArrayIterator(array, offset, length);
/* 183:    */  }
/* 184:    */  
/* 191:    */  public static CharListIterator wrap(char[] array)
/* 192:    */  {
/* 193:193 */    return new ArrayIterator(array, 0, array.length);
/* 194:    */  }
/* 195:    */  
/* 209:    */  public static int unwrap(CharIterator i, char[] array, int offset, int max)
/* 210:    */  {
/* 211:211 */    if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
/* 212:212 */    if ((offset < 0) || (offset + max > array.length)) throw new IllegalArgumentException();
/* 213:213 */    int j = max;
/* 214:214 */    while ((j-- != 0) && (i.hasNext())) array[(offset++)] = i.nextChar();
/* 215:215 */    return max - j - 1;
/* 216:    */  }
/* 217:    */  
/* 227:    */  public static int unwrap(CharIterator i, char[] array)
/* 228:    */  {
/* 229:229 */    return unwrap(i, array, 0, array.length);
/* 230:    */  }
/* 231:    */  
/* 242:    */  public static char[] unwrap(CharIterator i, int max)
/* 243:    */  {
/* 244:244 */    if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
/* 245:245 */    char[] array = new char[16];
/* 246:246 */    int j = 0;
/* 247:    */    
/* 248:248 */    while ((max-- != 0) && (i.hasNext())) {
/* 249:249 */      if (j == array.length) array = CharArrays.grow(array, j + 1);
/* 250:250 */      array[(j++)] = i.nextChar();
/* 251:    */    }
/* 252:    */    
/* 253:253 */    return CharArrays.trim(array, j);
/* 254:    */  }
/* 255:    */  
/* 265:    */  public static char[] unwrap(CharIterator i)
/* 266:    */  {
/* 267:267 */    return unwrap(i, 2147483647);
/* 268:    */  }
/* 269:    */  
/* 284:    */  public static int unwrap(CharIterator i, CharCollection c, int max)
/* 285:    */  {
/* 286:286 */    if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
/* 287:287 */    int j = max;
/* 288:288 */    while ((j-- != 0) && (i.hasNext())) c.add(i.nextChar());
/* 289:289 */    return max - j - 1;
/* 290:    */  }
/* 291:    */  
/* 303:    */  public static long unwrap(CharIterator i, CharCollection c)
/* 304:    */  {
/* 305:305 */    long n = 0L;
/* 306:306 */    while (i.hasNext()) {
/* 307:307 */      c.add(i.nextChar());
/* 308:308 */      n += 1L;
/* 309:    */    }
/* 310:310 */    return n;
/* 311:    */  }
/* 312:    */  
/* 326:    */  public static int pour(CharIterator i, CharCollection s, int max)
/* 327:    */  {
/* 328:328 */    if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative");
/* 329:329 */    int j = max;
/* 330:330 */    while ((j-- != 0) && (i.hasNext())) s.add(i.nextChar());
/* 331:331 */    return max - j - 1;
/* 332:    */  }
/* 333:    */  
/* 345:    */  public static int pour(CharIterator i, CharCollection s)
/* 346:    */  {
/* 347:347 */    return pour(i, s, 2147483647);
/* 348:    */  }
/* 349:    */  
/* 362:    */  public static CharList pour(CharIterator i, int max)
/* 363:    */  {
/* 364:364 */    CharArrayList l = new CharArrayList();
/* 365:365 */    pour(i, l, max);
/* 366:366 */    l.trim();
/* 367:367 */    return l;
/* 368:    */  }
/* 369:    */  
/* 380:    */  public static CharList pour(CharIterator i)
/* 381:    */  {
/* 382:382 */    return pour(i, 2147483647);
/* 383:    */  }
/* 384:    */  
/* 385:    */  private static class IteratorWrapper extends AbstractCharIterator {
/* 386:    */    final Iterator<Character> i;
/* 387:    */    
/* 388:    */    public IteratorWrapper(Iterator<Character> i) {
/* 389:389 */      this.i = i;
/* 390:    */    }
/* 391:    */    
/* 392:392 */    public boolean hasNext() { return this.i.hasNext(); }
/* 393:393 */    public void remove() { this.i.remove(); }
/* 394:    */    
/* 395:395 */    public char nextChar() { return ((Character)this.i.next()).charValue(); }
/* 396:    */  }
/* 397:    */  
/* 412:    */  public static CharIterator asCharIterator(Iterator i)
/* 413:    */  {
/* 414:414 */    if ((i instanceof CharIterator)) return (CharIterator)i;
/* 415:415 */    return new IteratorWrapper(i);
/* 416:    */  }
/* 417:    */  
/* 418:    */  private static class ListIteratorWrapper extends AbstractCharListIterator
/* 419:    */  {
/* 420:    */    final ListIterator<Character> i;
/* 421:    */    
/* 422:    */    public ListIteratorWrapper(ListIterator<Character> i) {
/* 423:423 */      this.i = i;
/* 424:    */    }
/* 425:    */    
/* 426:426 */    public boolean hasNext() { return this.i.hasNext(); }
/* 427:427 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 428:428 */    public int nextIndex() { return this.i.nextIndex(); }
/* 429:429 */    public int previousIndex() { return this.i.previousIndex(); }
/* 430:    */    
/* 431:431 */    public void set(char k) { this.i.set(Character.valueOf(k)); }
/* 432:    */    
/* 433:433 */    public void add(char k) { this.i.add(Character.valueOf(k)); }
/* 434:434 */    public void remove() { this.i.remove(); }
/* 435:    */    
/* 436:436 */    public char nextChar() { return ((Character)this.i.next()).charValue(); }
/* 437:437 */    public char previousChar() { return ((Character)this.i.previous()).charValue(); }
/* 438:    */  }
/* 439:    */  
/* 455:    */  public static CharListIterator asCharIterator(ListIterator i)
/* 456:    */  {
/* 457:457 */    if ((i instanceof CharListIterator)) return (CharListIterator)i;
/* 458:458 */    return new ListIteratorWrapper(i);
/* 459:    */  }
/* 460:    */  
/* 462:    */  private static class IntervalIterator
/* 463:    */    extends AbstractCharListIterator
/* 464:    */  {
/* 465:    */    private final char from;
/* 466:    */    
/* 467:    */    private final char to;
/* 468:    */    
/* 469:    */    char curr;
/* 470:    */    
/* 471:    */    public IntervalIterator(char from, char to)
/* 472:    */    {
/* 473:473 */      this.from = (this.curr = from);
/* 474:474 */      this.to = to;
/* 475:    */    }
/* 476:    */    
/* 477:477 */    public boolean hasNext() { return this.curr < this.to; }
/* 478:478 */    public boolean hasPrevious() { return this.curr > this.from; }
/* 479:    */    
/* 480:    */    public char nextChar() {
/* 481:481 */      if (!hasNext()) throw new NoSuchElementException();
/* 482:482 */      return this.curr++;
/* 483:    */    }
/* 484:    */    
/* 485:485 */    public char previousChar() { if (!hasPrevious()) throw new NoSuchElementException();
/* 486:486 */      return this.curr = (char)(this.curr - '\001');
/* 487:    */    }
/* 488:    */    
/* 490:490 */    public int nextIndex() { return this.curr - this.from; }
/* 491:491 */    public int previousIndex() { return this.curr - this.from - 1; }
/* 492:    */    
/* 493:    */    public int skip(int n)
/* 494:    */    {
/* 495:495 */      if (this.curr + n <= this.to) {
/* 496:496 */        this.curr = ((char)(this.curr + n));
/* 497:497 */        return n;
/* 498:    */      }
/* 499:    */      
/* 500:500 */      n = this.to - this.curr;
/* 501:    */      
/* 504:504 */      this.curr = this.to;
/* 505:505 */      return n;
/* 506:    */    }
/* 507:    */    
/* 508:    */    public int back(int n) {
/* 509:509 */      if (this.curr - n >= this.from) {
/* 510:510 */        this.curr = ((char)(this.curr - n));
/* 511:511 */        return n;
/* 512:    */      }
/* 513:    */      
/* 514:514 */      n = this.curr - this.from;
/* 515:    */      
/* 518:518 */      this.curr = this.from;
/* 519:519 */      return n;
/* 520:    */    }
/* 521:    */  }
/* 522:    */  
/* 532:532 */  public static CharListIterator fromTo(char from, char to) { return new IntervalIterator(from, to); }
/* 533:    */  
/* 534:    */  private static class IteratorConcatenator extends AbstractCharIterator { final CharIterator[] a;
/* 535:    */    int offset;
/* 536:536 */    int length; int lastOffset = -1;
/* 537:    */    
/* 538:538 */    public IteratorConcatenator(CharIterator[] a, int offset, int length) { this.a = a;
/* 539:539 */      this.offset = offset;
/* 540:540 */      this.length = length;
/* 541:541 */      advance();
/* 542:    */    }
/* 543:    */    
/* 544:544 */    private void advance() { while ((this.length != 0) && 
/* 545:545 */        (!this.a[this.offset].hasNext())) {
/* 546:546 */        this.length -= 1;
/* 547:547 */        this.offset += 1;
/* 548:    */      }
/* 549:    */    }
/* 550:    */    
/* 552:552 */    public boolean hasNext() { return this.length > 0; }
/* 553:    */    
/* 554:    */    public char nextChar() {
/* 555:555 */      if (!hasNext()) throw new NoSuchElementException();
/* 556:556 */      char next = this.a[(this.lastOffset = this.offset)].nextChar();
/* 557:557 */      advance();
/* 558:558 */      return next;
/* 559:    */    }
/* 560:    */    
/* 561:561 */    public void remove() { if (this.lastOffset == -1) throw new IllegalStateException();
/* 562:562 */      this.a[this.lastOffset].remove();
/* 563:    */    }
/* 564:    */    
/* 565:565 */    public int skip(int n) { this.lastOffset = -1;
/* 566:566 */      int skipped = 0;
/* 567:567 */      while ((skipped < n) && (this.length != 0)) {
/* 568:568 */        skipped += this.a[this.offset].skip(n - skipped);
/* 569:569 */        if (this.a[this.offset].hasNext()) break;
/* 570:570 */        this.length -= 1;
/* 571:571 */        this.offset += 1;
/* 572:    */      }
/* 573:573 */      return skipped;
/* 574:    */    }
/* 575:    */  }
/* 576:    */  
/* 583:    */  public static CharIterator concat(CharIterator[] a)
/* 584:    */  {
/* 585:585 */    return concat(a, 0, a.length);
/* 586:    */  }
/* 587:    */  
/* 598:    */  public static CharIterator concat(CharIterator[] a, int offset, int length)
/* 599:    */  {
/* 600:600 */    return new IteratorConcatenator(a, offset, length);
/* 601:    */  }
/* 602:    */  
/* 603:    */  public static class UnmodifiableIterator
/* 604:    */    extends AbstractCharIterator {
/* 605:    */    protected final CharIterator i;
/* 606:    */    
/* 607:607 */    public UnmodifiableIterator(CharIterator i) { this.i = i; }
/* 608:    */    
/* 609:609 */    public boolean hasNext() { return this.i.hasNext(); }
/* 610:610 */    public char nextChar() { return this.i.nextChar(); }
/* 611:611 */    public Character next() { return (Character)this.i.next(); }
/* 612:    */  }
/* 613:    */  
/* 616:    */  public static CharIterator unmodifiable(CharIterator i)
/* 617:    */  {
/* 618:618 */    return new UnmodifiableIterator(i);
/* 619:    */  }
/* 620:    */  
/* 621:    */  public static class UnmodifiableBidirectionalIterator extends AbstractCharBidirectionalIterator {
/* 622:    */    protected final CharBidirectionalIterator i;
/* 623:    */    
/* 624:624 */    public UnmodifiableBidirectionalIterator(CharBidirectionalIterator i) { this.i = i; }
/* 625:    */    
/* 626:626 */    public boolean hasNext() { return this.i.hasNext(); }
/* 627:627 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 628:628 */    public char nextChar() { return this.i.nextChar(); }
/* 629:629 */    public char previousChar() { return this.i.previousChar(); }
/* 630:630 */    public Character next() { return (Character)this.i.next(); }
/* 631:631 */    public Character previous() { return (Character)this.i.previous(); }
/* 632:    */  }
/* 633:    */  
/* 636:    */  public static CharBidirectionalIterator unmodifiable(CharBidirectionalIterator i)
/* 637:    */  {
/* 638:638 */    return new UnmodifiableBidirectionalIterator(i);
/* 639:    */  }
/* 640:    */  
/* 641:    */  public static class UnmodifiableListIterator extends AbstractCharListIterator {
/* 642:    */    protected final CharListIterator i;
/* 643:    */    
/* 644:644 */    public UnmodifiableListIterator(CharListIterator i) { this.i = i; }
/* 645:    */    
/* 646:646 */    public boolean hasNext() { return this.i.hasNext(); }
/* 647:647 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 648:648 */    public char nextChar() { return this.i.nextChar(); }
/* 649:649 */    public char previousChar() { return this.i.previousChar(); }
/* 650:650 */    public int nextIndex() { return this.i.nextIndex(); }
/* 651:651 */    public int previousIndex() { return this.i.previousIndex(); }
/* 652:652 */    public Character next() { return (Character)this.i.next(); }
/* 653:653 */    public Character previous() { return (Character)this.i.previous(); }
/* 654:    */  }
/* 655:    */  
/* 658:    */  public static CharListIterator unmodifiable(CharListIterator i)
/* 659:    */  {
/* 660:660 */    return new UnmodifiableListIterator(i);
/* 661:    */  }
/* 662:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.chars.CharIterators
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */