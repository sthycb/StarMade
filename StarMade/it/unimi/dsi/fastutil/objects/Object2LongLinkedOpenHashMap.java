/*    1:     */package it.unimi.dsi.fastutil.objects;
/*    2:     */
/*    3:     */import it.unimi.dsi.fastutil.Hash;
/*    4:     */import it.unimi.dsi.fastutil.HashCommon;
/*    5:     */import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*    6:     */import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*    7:     */import it.unimi.dsi.fastutil.longs.LongCollection;
/*    8:     */import it.unimi.dsi.fastutil.longs.LongIterator;
/*    9:     */import it.unimi.dsi.fastutil.longs.LongListIterator;
/*   10:     */import java.io.IOException;
/*   11:     */import java.io.ObjectInputStream;
/*   12:     */import java.io.ObjectOutputStream;
/*   13:     */import java.io.Serializable;
/*   14:     */import java.util.Comparator;
/*   15:     */import java.util.Map;
/*   16:     */import java.util.Map.Entry;
/*   17:     */import java.util.NoSuchElementException;
/*   18:     */
/*  113:     */public class Object2LongLinkedOpenHashMap<K>
/*  114:     */  extends AbstractObject2LongSortedMap<K>
/*  115:     */  implements Serializable, Cloneable, Hash
/*  116:     */{
/*  117:     */  public static final long serialVersionUID = 0L;
/*  118:     */  private static final boolean ASSERTS = false;
/*  119:     */  protected transient K[] key;
/*  120:     */  protected transient long[] value;
/*  121:     */  protected transient boolean[] used;
/*  122:     */  protected final float f;
/*  123:     */  protected transient int n;
/*  124:     */  protected transient int maxFill;
/*  125:     */  protected transient int mask;
/*  126:     */  protected int size;
/*  127:     */  protected volatile transient Object2LongSortedMap.FastSortedEntrySet<K> entries;
/*  128:     */  protected volatile transient ObjectSortedSet<K> keys;
/*  129:     */  protected volatile transient LongCollection values;
/*  130: 130 */  protected transient int first = -1;
/*  131:     */  
/*  132: 132 */  protected transient int last = -1;
/*  133:     */  
/*  140:     */  protected transient long[] link;
/*  141:     */  
/*  149:     */  public Object2LongLinkedOpenHashMap(int expected, float f)
/*  150:     */  {
/*  151: 151 */    if ((f <= 0.0F) || (f > 1.0F)) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
/*  152: 152 */    if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative");
/*  153: 153 */    this.f = f;
/*  154: 154 */    this.n = HashCommon.arraySize(expected, f);
/*  155: 155 */    this.mask = (this.n - 1);
/*  156: 156 */    this.maxFill = HashCommon.maxFill(this.n, f);
/*  157: 157 */    this.key = ((Object[])new Object[this.n]);
/*  158: 158 */    this.value = new long[this.n];
/*  159: 159 */    this.used = new boolean[this.n];
/*  160: 160 */    this.link = new long[this.n];
/*  161:     */  }
/*  162:     */  
/*  165:     */  public Object2LongLinkedOpenHashMap(int expected)
/*  166:     */  {
/*  167: 167 */    this(expected, 0.75F);
/*  168:     */  }
/*  169:     */  
/*  171:     */  public Object2LongLinkedOpenHashMap()
/*  172:     */  {
/*  173: 173 */    this(16, 0.75F);
/*  174:     */  }
/*  175:     */  
/*  179:     */  public Object2LongLinkedOpenHashMap(Map<? extends K, ? extends Long> m, float f)
/*  180:     */  {
/*  181: 181 */    this(m.size(), f);
/*  182: 182 */    putAll(m);
/*  183:     */  }
/*  184:     */  
/*  187:     */  public Object2LongLinkedOpenHashMap(Map<? extends K, ? extends Long> m)
/*  188:     */  {
/*  189: 189 */    this(m, 0.75F);
/*  190:     */  }
/*  191:     */  
/*  195:     */  public Object2LongLinkedOpenHashMap(Object2LongMap<K> m, float f)
/*  196:     */  {
/*  197: 197 */    this(m.size(), f);
/*  198: 198 */    putAll(m);
/*  199:     */  }
/*  200:     */  
/*  203:     */  public Object2LongLinkedOpenHashMap(Object2LongMap<K> m)
/*  204:     */  {
/*  205: 205 */    this(m, 0.75F);
/*  206:     */  }
/*  207:     */  
/*  213:     */  public Object2LongLinkedOpenHashMap(K[] k, long[] v, float f)
/*  214:     */  {
/*  215: 215 */    this(k.length, f);
/*  216: 216 */    if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*  217: 217 */    for (int i = 0; i < k.length; i++) { put(k[i], v[i]);
/*  218:     */    }
/*  219:     */  }
/*  220:     */  
/*  224:     */  public Object2LongLinkedOpenHashMap(K[] k, long[] v)
/*  225:     */  {
/*  226: 226 */    this(k, v, 0.75F);
/*  227:     */  }
/*  228:     */  
/*  232:     */  public long put(K k, long v)
/*  233:     */  {
/*  234: 234 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  235:     */    
/*  236: 236 */    while (this.used[pos] != 0) {
/*  237: 237 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) {
/*  238: 238 */        long oldValue = this.value[pos];
/*  239: 239 */        this.value[pos] = v;
/*  240: 240 */        return oldValue;
/*  241:     */      }
/*  242: 242 */      pos = pos + 1 & this.mask;
/*  243:     */    }
/*  244: 244 */    this.used[pos] = true;
/*  245: 245 */    this.key[pos] = k;
/*  246: 246 */    this.value[pos] = v;
/*  247: 247 */    if (this.size == 0) {
/*  248: 248 */      this.first = (this.last = pos);
/*  249:     */      
/*  250: 250 */      this.link[pos] = -1L;
/*  251:     */    }
/*  252:     */    else {
/*  253: 253 */      this.link[this.last] ^= (this.link[this.last] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  254: 254 */      this.link[pos] = ((this.last & 0xFFFFFFFF) << 32 | 0xFFFFFFFF);
/*  255: 255 */      this.last = pos;
/*  256:     */    }
/*  257: 257 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/*  258:     */    }
/*  259: 259 */    return this.defRetValue;
/*  260:     */  }
/*  261:     */  
/*  262: 262 */  public Long put(K ok, Long ov) { long v = ov.longValue();
/*  263: 263 */    K k = ok;
/*  264:     */    
/*  265: 265 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  266:     */    
/*  267: 267 */    while (this.used[pos] != 0) {
/*  268: 268 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) {
/*  269: 269 */        Long oldValue = Long.valueOf(this.value[pos]);
/*  270: 270 */        this.value[pos] = v;
/*  271: 271 */        return oldValue;
/*  272:     */      }
/*  273: 273 */      pos = pos + 1 & this.mask;
/*  274:     */    }
/*  275: 275 */    this.used[pos] = true;
/*  276: 276 */    this.key[pos] = k;
/*  277: 277 */    this.value[pos] = v;
/*  278: 278 */    if (this.size == 0) {
/*  279: 279 */      this.first = (this.last = pos);
/*  280:     */      
/*  281: 281 */      this.link[pos] = -1L;
/*  282:     */    }
/*  283:     */    else {
/*  284: 284 */      this.link[this.last] ^= (this.link[this.last] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  285: 285 */      this.link[pos] = ((this.last & 0xFFFFFFFF) << 32 | 0xFFFFFFFF);
/*  286: 286 */      this.last = pos;
/*  287:     */    }
/*  288: 288 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/*  289:     */    }
/*  290: 290 */    return null;
/*  291:     */  }
/*  292:     */  
/*  303:     */  public long add(K k, long incr)
/*  304:     */  {
/*  305: 305 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  306:     */    
/*  307: 307 */    while (this.used[pos] != 0) {
/*  308: 308 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) {
/*  309: 309 */        long oldValue = this.value[pos];
/*  310: 310 */        this.value[pos] += incr;
/*  311: 311 */        return oldValue;
/*  312:     */      }
/*  313: 313 */      pos = pos + 1 & this.mask;
/*  314:     */    }
/*  315: 315 */    this.used[pos] = true;
/*  316: 316 */    this.key[pos] = k;
/*  317: 317 */    this.value[pos] = (this.defRetValue + incr);
/*  318: 318 */    if (this.size == 0) {
/*  319: 319 */      this.first = (this.last = pos);
/*  320:     */      
/*  321: 321 */      this.link[pos] = -1L;
/*  322:     */    }
/*  323:     */    else {
/*  324: 324 */      this.link[this.last] ^= (this.link[this.last] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  325: 325 */      this.link[pos] = ((this.last & 0xFFFFFFFF) << 32 | 0xFFFFFFFF);
/*  326: 326 */      this.last = pos;
/*  327:     */    }
/*  328: 328 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size + 1, this.f));
/*  329:     */    }
/*  330: 330 */    return this.defRetValue;
/*  331:     */  }
/*  332:     */  
/*  335:     */  protected final int shiftKeys(int pos)
/*  336:     */  {
/*  337:     */    int last;
/*  338:     */    
/*  340:     */    for (;;)
/*  341:     */    {
/*  342: 342 */      pos = (last = pos) + 1 & this.mask;
/*  343: 343 */      while (this.used[pos] != 0) {
/*  344: 344 */        int slot = (this.key[pos] == null ? 142593372 : HashCommon.murmurHash3(this.key[pos].hashCode())) & this.mask;
/*  345: 345 */        if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/*  346: 346 */        pos = pos + 1 & this.mask;
/*  347:     */      }
/*  348: 348 */      if (this.used[pos] == 0) break;
/*  349: 349 */      this.key[last] = this.key[pos];
/*  350: 350 */      this.value[last] = this.value[pos];
/*  351: 351 */      fixPointers(pos, last);
/*  352:     */    }
/*  353: 353 */    this.used[last] = false;
/*  354: 354 */    this.key[last] = null;
/*  355: 355 */    return last;
/*  356:     */  }
/*  357:     */  
/*  358:     */  public long removeLong(Object k)
/*  359:     */  {
/*  360: 360 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  361:     */    
/*  362: 362 */    while (this.used[pos] != 0) {
/*  363: 363 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) {
/*  364: 364 */        this.size -= 1;
/*  365: 365 */        fixPointers(pos);
/*  366: 366 */        long v = this.value[pos];
/*  367: 367 */        shiftKeys(pos);
/*  368: 368 */        return v;
/*  369:     */      }
/*  370: 370 */      pos = pos + 1 & this.mask;
/*  371:     */    }
/*  372: 372 */    return this.defRetValue;
/*  373:     */  }
/*  374:     */  
/*  375:     */  public Long remove(Object ok) {
/*  376: 376 */    K k = ok;
/*  377:     */    
/*  378: 378 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  379:     */    
/*  380: 380 */    while (this.used[pos] != 0) {
/*  381: 381 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) {
/*  382: 382 */        this.size -= 1;
/*  383: 383 */        fixPointers(pos);
/*  384: 384 */        long v = this.value[pos];
/*  385: 385 */        shiftKeys(pos);
/*  386: 386 */        return Long.valueOf(v);
/*  387:     */      }
/*  388: 388 */      pos = pos + 1 & this.mask;
/*  389:     */    }
/*  390: 390 */    return null;
/*  391:     */  }
/*  392:     */  
/*  395:     */  public long removeFirstLong()
/*  396:     */  {
/*  397: 397 */    if (this.size == 0) throw new NoSuchElementException();
/*  398: 398 */    this.size -= 1;
/*  399: 399 */    int pos = this.first;
/*  400:     */    
/*  401: 401 */    this.first = ((int)this.link[pos]);
/*  402: 402 */    if (0 <= this.first)
/*  403:     */    {
/*  404: 404 */      this.link[this.first] |= -4294967296L;
/*  405:     */    }
/*  406: 406 */    long v = this.value[pos];
/*  407: 407 */    shiftKeys(pos);
/*  408: 408 */    return v;
/*  409:     */  }
/*  410:     */  
/*  413:     */  public long removeLastLong()
/*  414:     */  {
/*  415: 415 */    if (this.size == 0) throw new NoSuchElementException();
/*  416: 416 */    this.size -= 1;
/*  417: 417 */    int pos = this.last;
/*  418:     */    
/*  419: 419 */    this.last = ((int)(this.link[pos] >>> 32));
/*  420: 420 */    if (0 <= this.last)
/*  421:     */    {
/*  422: 422 */      this.link[this.last] |= 4294967295L;
/*  423:     */    }
/*  424: 424 */    long v = this.value[pos];
/*  425: 425 */    shiftKeys(pos);
/*  426: 426 */    return v;
/*  427:     */  }
/*  428:     */  
/*  429: 429 */  private void moveIndexToFirst(int i) { if ((this.size == 1) || (this.first == i)) return;
/*  430: 430 */    if (this.last == i) {
/*  431: 431 */      this.last = ((int)(this.link[i] >>> 32));
/*  432:     */      
/*  433: 433 */      this.link[this.last] |= 4294967295L;
/*  434:     */    }
/*  435:     */    else {
/*  436: 436 */      long linki = this.link[i];
/*  437: 437 */      int prev = (int)(linki >>> 32);
/*  438: 438 */      int next = (int)linki;
/*  439: 439 */      this.link[prev] ^= (this.link[prev] ^ linki & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  440: 440 */      this.link[next] ^= (this.link[next] ^ linki & 0x0) & 0x0;
/*  441:     */    }
/*  442: 442 */    this.link[this.first] ^= (this.link[this.first] ^ (i & 0xFFFFFFFF) << 32) & 0x0;
/*  443: 443 */    this.link[i] = (0x0 | this.first & 0xFFFFFFFF);
/*  444: 444 */    this.first = i;
/*  445:     */  }
/*  446:     */  
/*  447: 447 */  private void moveIndexToLast(int i) { if ((this.size == 1) || (this.last == i)) return;
/*  448: 448 */    if (this.first == i) {
/*  449: 449 */      this.first = ((int)this.link[i]);
/*  450:     */      
/*  451: 451 */      this.link[this.first] |= -4294967296L;
/*  452:     */    }
/*  453:     */    else {
/*  454: 454 */      long linki = this.link[i];
/*  455: 455 */      int prev = (int)(linki >>> 32);
/*  456: 456 */      int next = (int)linki;
/*  457: 457 */      this.link[prev] ^= (this.link[prev] ^ linki & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  458: 458 */      this.link[next] ^= (this.link[next] ^ linki & 0x0) & 0x0;
/*  459:     */    }
/*  460: 460 */    this.link[this.last] ^= (this.link[this.last] ^ i & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  461: 461 */    this.link[i] = ((this.last & 0xFFFFFFFF) << 32 | 0xFFFFFFFF);
/*  462: 462 */    this.last = i;
/*  463:     */  }
/*  464:     */  
/*  468:     */  public long getAndMoveToFirst(K k)
/*  469:     */  {
/*  470: 470 */    K[] key = this.key;
/*  471: 471 */    boolean[] used = this.used;
/*  472: 472 */    int mask = this.mask;
/*  473:     */    
/*  474: 474 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & mask;
/*  475:     */    
/*  476: 476 */    while (used[pos] != 0) {
/*  477: 477 */      if (k == null ? key[pos] == null : k.equals(key[pos])) {
/*  478: 478 */        moveIndexToFirst(pos);
/*  479: 479 */        return this.value[pos];
/*  480:     */      }
/*  481: 481 */      pos = pos + 1 & mask;
/*  482:     */    }
/*  483: 483 */    return this.defRetValue;
/*  484:     */  }
/*  485:     */  
/*  489:     */  public long getAndMoveToLast(K k)
/*  490:     */  {
/*  491: 491 */    K[] key = this.key;
/*  492: 492 */    boolean[] used = this.used;
/*  493: 493 */    int mask = this.mask;
/*  494:     */    
/*  495: 495 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & mask;
/*  496:     */    
/*  497: 497 */    while (used[pos] != 0) {
/*  498: 498 */      if (k == null ? key[pos] == null : k.equals(key[pos])) {
/*  499: 499 */        moveIndexToLast(pos);
/*  500: 500 */        return this.value[pos];
/*  501:     */      }
/*  502: 502 */      pos = pos + 1 & mask;
/*  503:     */    }
/*  504: 504 */    return this.defRetValue;
/*  505:     */  }
/*  506:     */  
/*  511:     */  public long putAndMoveToFirst(K k, long v)
/*  512:     */  {
/*  513: 513 */    K[] key = this.key;
/*  514: 514 */    boolean[] used = this.used;
/*  515: 515 */    int mask = this.mask;
/*  516:     */    
/*  517: 517 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & mask;
/*  518:     */    
/*  519: 519 */    while (used[pos] != 0) {
/*  520: 520 */      if (k == null ? key[pos] == null : k.equals(key[pos])) {
/*  521: 521 */        long oldValue = this.value[pos];
/*  522: 522 */        this.value[pos] = v;
/*  523: 523 */        moveIndexToFirst(pos);
/*  524: 524 */        return oldValue;
/*  525:     */      }
/*  526: 526 */      pos = pos + 1 & mask;
/*  527:     */    }
/*  528: 528 */    used[pos] = true;
/*  529: 529 */    key[pos] = k;
/*  530: 530 */    this.value[pos] = v;
/*  531: 531 */    if (this.size == 0) {
/*  532: 532 */      this.first = (this.last = pos);
/*  533:     */      
/*  534: 534 */      this.link[pos] = -1L;
/*  535:     */    }
/*  536:     */    else {
/*  537: 537 */      this.link[this.first] ^= (this.link[this.first] ^ (pos & 0xFFFFFFFF) << 32) & 0x0;
/*  538: 538 */      this.link[pos] = (0x0 | this.first & 0xFFFFFFFF);
/*  539: 539 */      this.first = pos;
/*  540:     */    }
/*  541: 541 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size, this.f));
/*  542:     */    }
/*  543: 543 */    return this.defRetValue;
/*  544:     */  }
/*  545:     */  
/*  550:     */  public long putAndMoveToLast(K k, long v)
/*  551:     */  {
/*  552: 552 */    K[] key = this.key;
/*  553: 553 */    boolean[] used = this.used;
/*  554: 554 */    int mask = this.mask;
/*  555:     */    
/*  556: 556 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & mask;
/*  557:     */    
/*  558: 558 */    while (used[pos] != 0) {
/*  559: 559 */      if (k == null ? key[pos] == null : k.equals(key[pos])) {
/*  560: 560 */        long oldValue = this.value[pos];
/*  561: 561 */        this.value[pos] = v;
/*  562: 562 */        moveIndexToLast(pos);
/*  563: 563 */        return oldValue;
/*  564:     */      }
/*  565: 565 */      pos = pos + 1 & mask;
/*  566:     */    }
/*  567: 567 */    used[pos] = true;
/*  568: 568 */    key[pos] = k;
/*  569: 569 */    this.value[pos] = v;
/*  570: 570 */    if (this.size == 0) {
/*  571: 571 */      this.first = (this.last = pos);
/*  572:     */      
/*  573: 573 */      this.link[pos] = -1L;
/*  574:     */    }
/*  575:     */    else {
/*  576: 576 */      this.link[this.last] ^= (this.link[this.last] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  577: 577 */      this.link[pos] = ((this.last & 0xFFFFFFFF) << 32 | 0xFFFFFFFF);
/*  578: 578 */      this.last = pos;
/*  579:     */    }
/*  580: 580 */    if (++this.size >= this.maxFill) { rehash(HashCommon.arraySize(this.size, this.f));
/*  581:     */    }
/*  582: 582 */    return this.defRetValue;
/*  583:     */  }
/*  584:     */  
/*  585:     */  public long getLong(Object k)
/*  586:     */  {
/*  587: 587 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  588:     */    
/*  589: 589 */    while (this.used[pos] != 0) {
/*  590: 590 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) return this.value[pos];
/*  591: 591 */      pos = pos + 1 & this.mask;
/*  592:     */    }
/*  593: 593 */    return this.defRetValue;
/*  594:     */  }
/*  595:     */  
/*  596:     */  public boolean containsKey(Object k)
/*  597:     */  {
/*  598: 598 */    int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/*  599:     */    
/*  600: 600 */    while (this.used[pos] != 0) {
/*  601: 601 */      if (this.key[pos] == null ? k == null : this.key[pos].equals(k)) return true;
/*  602: 602 */      pos = pos + 1 & this.mask;
/*  603:     */    }
/*  604: 604 */    return false;
/*  605:     */  }
/*  606:     */  
/*  607: 607 */  public boolean containsValue(long v) { long[] value = this.value;
/*  608: 608 */    boolean[] used = this.used;
/*  609: 609 */    for (int i = this.n; i-- != 0; return true) label17: if ((used[i] == 0) || (value[i] != v)) break label17;
/*  610: 610 */    return false;
/*  611:     */  }
/*  612:     */  
/*  617:     */  public void clear()
/*  618:     */  {
/*  619: 619 */    if (this.size == 0) return;
/*  620: 620 */    this.size = 0;
/*  621: 621 */    BooleanArrays.fill(this.used, false);
/*  622:     */    
/*  623: 623 */    ObjectArrays.fill(this.key, null);
/*  624: 624 */    this.first = (this.last = -1);
/*  625:     */  }
/*  626:     */  
/*  627: 627 */  public int size() { return this.size; }
/*  628:     */  
/*  629:     */  public boolean isEmpty() {
/*  630: 630 */    return this.size == 0;
/*  631:     */  }
/*  632:     */  
/*  637:     */  @Deprecated
/*  638:     */  public void growthFactor(int growthFactor) {}
/*  639:     */  
/*  644:     */  @Deprecated
/*  645:     */  public int growthFactor()
/*  646:     */  {
/*  647: 647 */    return 16;
/*  648:     */  }
/*  649:     */  
/*  650:     */  private final class MapEntry
/*  651:     */    implements Object2LongMap.Entry<K>, Map.Entry<K, Long>
/*  652:     */  {
/*  653:     */    private int index;
/*  654:     */    
/*  655:     */    MapEntry(int index)
/*  656:     */    {
/*  657: 657 */      this.index = index;
/*  658:     */    }
/*  659:     */    
/*  660: 660 */    public K getKey() { return Object2LongLinkedOpenHashMap.this.key[this.index]; }
/*  661:     */    
/*  662:     */    public Long getValue() {
/*  663: 663 */      return Long.valueOf(Object2LongLinkedOpenHashMap.this.value[this.index]);
/*  664:     */    }
/*  665:     */    
/*  666: 666 */    public long getLongValue() { return Object2LongLinkedOpenHashMap.this.value[this.index]; }
/*  667:     */    
/*  668:     */    public long setValue(long v) {
/*  669: 669 */      long oldValue = Object2LongLinkedOpenHashMap.this.value[this.index];
/*  670: 670 */      Object2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  671: 671 */      return oldValue;
/*  672:     */    }
/*  673:     */    
/*  674: 674 */    public Long setValue(Long v) { return Long.valueOf(setValue(v.longValue())); }
/*  675:     */    
/*  676:     */    public boolean equals(Object o)
/*  677:     */    {
/*  678: 678 */      if (!(o instanceof Map.Entry)) return false;
/*  679: 679 */      Map.Entry<K, Long> e = (Map.Entry)o;
/*  680: 680 */      return (Object2LongLinkedOpenHashMap.this.key[this.index] == null ? e.getKey() == null : Object2LongLinkedOpenHashMap.this.key[this.index].equals(e.getKey())) && (Object2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*  681:     */    }
/*  682:     */    
/*  683: 683 */    public int hashCode() { return (Object2LongLinkedOpenHashMap.this.key[this.index] == null ? 0 : Object2LongLinkedOpenHashMap.this.key[this.index].hashCode()) ^ HashCommon.long2int(Object2LongLinkedOpenHashMap.this.value[this.index]); }
/*  684:     */    
/*  685:     */    public String toString() {
/*  686: 686 */      return Object2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Object2LongLinkedOpenHashMap.this.value[this.index];
/*  687:     */    }
/*  688:     */  }
/*  689:     */  
/*  695:     */  protected void fixPointers(int i)
/*  696:     */  {
/*  697: 697 */    if (this.size == 0) {
/*  698: 698 */      this.first = (this.last = -1);
/*  699: 699 */      return;
/*  700:     */    }
/*  701: 701 */    if (this.first == i) {
/*  702: 702 */      this.first = ((int)this.link[i]);
/*  703: 703 */      if (0 <= this.first)
/*  704:     */      {
/*  705: 705 */        this.link[this.first] |= -4294967296L;
/*  706:     */      }
/*  707: 707 */      return;
/*  708:     */    }
/*  709: 709 */    if (this.last == i) {
/*  710: 710 */      this.last = ((int)(this.link[i] >>> 32));
/*  711: 711 */      if (0 <= this.last)
/*  712:     */      {
/*  713: 713 */        this.link[this.last] |= 4294967295L;
/*  714:     */      }
/*  715: 715 */      return;
/*  716:     */    }
/*  717: 717 */    long linki = this.link[i];
/*  718: 718 */    int prev = (int)(linki >>> 32);
/*  719: 719 */    int next = (int)linki;
/*  720: 720 */    this.link[prev] ^= (this.link[prev] ^ linki & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  721: 721 */    this.link[next] ^= (this.link[next] ^ linki & 0x0) & 0x0;
/*  722:     */  }
/*  723:     */  
/*  730:     */  protected void fixPointers(int s, int d)
/*  731:     */  {
/*  732: 732 */    if (this.size == 1) {
/*  733: 733 */      this.first = (this.last = d);
/*  734:     */      
/*  735: 735 */      this.link[d] = -1L;
/*  736: 736 */      return;
/*  737:     */    }
/*  738: 738 */    if (this.first == s) {
/*  739: 739 */      this.first = d;
/*  740: 740 */      this.link[((int)this.link[s])] ^= (this.link[((int)this.link[s])] ^ (d & 0xFFFFFFFF) << 32) & 0x0;
/*  741: 741 */      this.link[d] = this.link[s];
/*  742: 742 */      return;
/*  743:     */    }
/*  744: 744 */    if (this.last == s) {
/*  745: 745 */      this.last = d;
/*  746: 746 */      this.link[((int)(this.link[s] >>> 32))] ^= (this.link[((int)(this.link[s] >>> 32))] ^ d & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  747: 747 */      this.link[d] = this.link[s];
/*  748: 748 */      return;
/*  749:     */    }
/*  750: 750 */    long links = this.link[s];
/*  751: 751 */    int prev = (int)(links >>> 32);
/*  752: 752 */    int next = (int)links;
/*  753: 753 */    this.link[prev] ^= (this.link[prev] ^ d & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  754: 754 */    this.link[next] ^= (this.link[next] ^ (d & 0xFFFFFFFF) << 32) & 0x0;
/*  755: 755 */    this.link[d] = links;
/*  756:     */  }
/*  757:     */  
/*  760:     */  public K firstKey()
/*  761:     */  {
/*  762: 762 */    if (this.size == 0) throw new NoSuchElementException();
/*  763: 763 */    return this.key[this.first];
/*  764:     */  }
/*  765:     */  
/*  768:     */  public K lastKey()
/*  769:     */  {
/*  770: 770 */    if (this.size == 0) throw new NoSuchElementException();
/*  771: 771 */    return this.key[this.last]; }
/*  772:     */  
/*  773: 773 */  public Comparator<? super K> comparator() { return null; }
/*  774: 774 */  public Object2LongSortedMap<K> tailMap(K from) { throw new UnsupportedOperationException(); }
/*  775: 775 */  public Object2LongSortedMap<K> headMap(K to) { throw new UnsupportedOperationException(); }
/*  776: 776 */  public Object2LongSortedMap<K> subMap(K from, K to) { throw new UnsupportedOperationException(); }
/*  777:     */  
/*  783:     */  private class MapIterator
/*  784:     */  {
/*  785: 785 */    int prev = -1;
/*  786:     */    
/*  787: 787 */    int next = -1;
/*  788:     */    
/*  789: 789 */    int curr = -1;
/*  790:     */    
/*  791: 791 */    int index = -1;
/*  792:     */    
/*  793: 793 */    private MapIterator() { this.next = Object2LongLinkedOpenHashMap.this.first;
/*  794: 794 */      this.index = 0;
/*  795:     */    }
/*  796:     */    
/*  797: 797 */    private MapIterator() { if (Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.last] == null ? from == null : Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.last].equals(from)) {
/*  798: 798 */        this.prev = Object2LongLinkedOpenHashMap.this.last;
/*  799: 799 */        this.index = Object2LongLinkedOpenHashMap.this.size;
/*  800:     */      }
/*  801:     */      else
/*  802:     */      {
/*  803: 803 */        int pos = (from == null ? 142593372 : HashCommon.murmurHash3(from.hashCode())) & Object2LongLinkedOpenHashMap.this.mask;
/*  804:     */        
/*  805: 805 */        while (Object2LongLinkedOpenHashMap.this.used[pos] != 0) {
/*  806: 806 */          if (Object2LongLinkedOpenHashMap.this.key[pos] == null ? from == null : Object2LongLinkedOpenHashMap.this.key[pos].equals(from))
/*  807:     */          {
/*  808: 808 */            this.next = ((int)Object2LongLinkedOpenHashMap.this.link[pos]);
/*  809: 809 */            this.prev = pos;
/*  810: 810 */            return;
/*  811:     */          }
/*  812: 812 */          pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*  813:     */        }
/*  814: 814 */        throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*  815:     */      } }
/*  816:     */    
/*  817: 817 */    public boolean hasNext() { return this.next != -1; }
/*  818: 818 */    public boolean hasPrevious() { return this.prev != -1; }
/*  819:     */    
/*  820: 820 */    private final void ensureIndexKnown() { if (this.index >= 0) return;
/*  821: 821 */      if (this.prev == -1) {
/*  822: 822 */        this.index = 0;
/*  823: 823 */        return;
/*  824:     */      }
/*  825: 825 */      if (this.next == -1) {
/*  826: 826 */        this.index = Object2LongLinkedOpenHashMap.this.size;
/*  827: 827 */        return;
/*  828:     */      }
/*  829: 829 */      int pos = Object2LongLinkedOpenHashMap.this.first;
/*  830: 830 */      this.index = 1;
/*  831: 831 */      while (pos != this.prev) {
/*  832: 832 */        pos = (int)Object2LongLinkedOpenHashMap.this.link[pos];
/*  833: 833 */        this.index += 1;
/*  834:     */      }
/*  835:     */    }
/*  836:     */    
/*  837: 837 */    public int nextIndex() { ensureIndexKnown();
/*  838: 838 */      return this.index;
/*  839:     */    }
/*  840:     */    
/*  841: 841 */    public int previousIndex() { ensureIndexKnown();
/*  842: 842 */      return this.index - 1;
/*  843:     */    }
/*  844:     */    
/*  845: 845 */    public int nextEntry() { if (!hasNext()) return Object2LongLinkedOpenHashMap.this.size();
/*  846: 846 */      this.curr = this.next;
/*  847: 847 */      this.next = ((int)Object2LongLinkedOpenHashMap.this.link[this.curr]);
/*  848: 848 */      this.prev = this.curr;
/*  849: 849 */      if (this.index >= 0) this.index += 1;
/*  850: 850 */      return this.curr;
/*  851:     */    }
/*  852:     */    
/*  853: 853 */    public int previousEntry() { if (!hasPrevious()) return -1;
/*  854: 854 */      this.curr = this.prev;
/*  855: 855 */      this.prev = ((int)(Object2LongLinkedOpenHashMap.this.link[this.curr] >>> 32));
/*  856: 856 */      this.next = this.curr;
/*  857: 857 */      if (this.index >= 0) this.index -= 1;
/*  858: 858 */      return this.curr;
/*  859:     */    }
/*  860:     */    
/*  861:     */    public void remove() {
/*  862: 862 */      ensureIndexKnown();
/*  863: 863 */      if (this.curr == -1) throw new IllegalStateException();
/*  864: 864 */      if (this.curr == this.prev)
/*  865:     */      {
/*  867: 867 */        this.index -= 1;
/*  868: 868 */        this.prev = ((int)(Object2LongLinkedOpenHashMap.this.link[this.curr] >>> 32));
/*  869:     */      }
/*  870:     */      else {
/*  871: 871 */        this.next = ((int)Object2LongLinkedOpenHashMap.this.link[this.curr]); }
/*  872: 872 */      Object2LongLinkedOpenHashMap.this.size -= 1;
/*  873:     */      
/*  875: 875 */      if (this.prev == -1) { Object2LongLinkedOpenHashMap.this.first = this.next;
/*  876:     */      } else
/*  877: 877 */        Object2LongLinkedOpenHashMap.this.link[this.prev] ^= (Object2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFF) & 0xFFFFFFFF;
/*  878: 878 */      if (this.next == -1) { Object2LongLinkedOpenHashMap.this.last = this.prev;
/*  879:     */      } else
/*  880: 880 */        Object2LongLinkedOpenHashMap.this.link[this.next] ^= (Object2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFF) << 32) & 0x0;
/*  881: 881 */      int pos = this.curr;
/*  882:     */      int last;
/*  883:     */      for (;;) {
/*  884: 884 */        pos = (last = pos) + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*  885: 885 */        while (Object2LongLinkedOpenHashMap.this.used[pos] != 0) {
/*  886: 886 */          int slot = (Object2LongLinkedOpenHashMap.this.key[pos] == null ? 142593372 : HashCommon.murmurHash3(Object2LongLinkedOpenHashMap.this.key[pos].hashCode())) & Object2LongLinkedOpenHashMap.this.mask;
/*  887: 887 */          if (last <= pos ? (last < slot) && (slot <= pos) : (last >= slot) && (slot > pos)) break;
/*  888: 888 */          pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*  889:     */        }
/*  890: 890 */        if (Object2LongLinkedOpenHashMap.this.used[pos] == 0) break;
/*  891: 891 */        Object2LongLinkedOpenHashMap.this.key[last] = Object2LongLinkedOpenHashMap.this.key[pos];
/*  892: 892 */        Object2LongLinkedOpenHashMap.this.value[last] = Object2LongLinkedOpenHashMap.this.value[pos];
/*  893: 893 */        if (this.next == pos) this.next = last;
/*  894: 894 */        if (this.prev == pos) this.prev = last;
/*  895: 895 */        Object2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*  896:     */      }
/*  897: 897 */      Object2LongLinkedOpenHashMap.this.used[last] = false;
/*  898: 898 */      Object2LongLinkedOpenHashMap.this.key[last] = null;
/*  899: 899 */      this.curr = -1;
/*  900:     */    }
/*  901:     */    
/*  902: 902 */    public int skip(int n) { int i = n;
/*  903: 903 */      while ((i-- != 0) && (hasNext())) nextEntry();
/*  904: 904 */      return n - i - 1;
/*  905:     */    }
/*  906:     */    
/*  907: 907 */    public int back(int n) { int i = n;
/*  908: 908 */      while ((i-- != 0) && (hasPrevious())) previousEntry();
/*  909: 909 */      return n - i - 1;
/*  910:     */    } }
/*  911:     */  
/*  912:     */  private class EntryIterator extends Object2LongLinkedOpenHashMap<K>.MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { private Object2LongLinkedOpenHashMap<K>.MapEntry entry;
/*  913:     */    
/*  914: 914 */    public EntryIterator() { super(null); }
/*  915:     */    
/*  916: 916 */    public EntryIterator() { super(from, null); }
/*  917:     */    
/*  918:     */    public Object2LongLinkedOpenHashMap<K>.MapEntry next() {
/*  919: 919 */      return this.entry = new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this, nextEntry());
/*  920:     */    }
/*  921:     */    
/*  922: 922 */    public Object2LongLinkedOpenHashMap<K>.MapEntry previous() { return this.entry = new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this, previousEntry()); }
/*  923:     */    
/*  924:     */    public void remove()
/*  925:     */    {
/*  926: 926 */      super.remove();
/*  927: 927 */      Object2LongLinkedOpenHashMap.MapEntry.access$202(this.entry, -1); }
/*  928:     */    
/*  929: 929 */    public void set(Object2LongMap.Entry<K> ok) { throw new UnsupportedOperationException(); }
/*  930: 930 */    public void add(Object2LongMap.Entry<K> ok) { throw new UnsupportedOperationException(); }
/*  931:     */  }
/*  932:     */  
/*  933: 933 */  private class FastEntryIterator extends Object2LongLinkedOpenHashMap<K>.MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { final AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry(null, 0L);
/*  934: 934 */    public FastEntryIterator() { super(null); }
/*  935:     */    
/*  936: 936 */    public FastEntryIterator() { super(from, null); }
/*  937:     */    
/*  938:     */    public AbstractObject2LongMap.BasicEntry<K> next() {
/*  939: 939 */      int e = nextEntry();
/*  940: 940 */      this.entry.key = Object2LongLinkedOpenHashMap.this.key[e];
/*  941: 941 */      this.entry.value = Object2LongLinkedOpenHashMap.this.value[e];
/*  942: 942 */      return this.entry;
/*  943:     */    }
/*  944:     */    
/*  945: 945 */    public AbstractObject2LongMap.BasicEntry<K> previous() { int e = previousEntry();
/*  946: 946 */      this.entry.key = Object2LongLinkedOpenHashMap.this.key[e];
/*  947: 947 */      this.entry.value = Object2LongLinkedOpenHashMap.this.value[e];
/*  948: 948 */      return this.entry; }
/*  949:     */    
/*  950: 950 */    public void set(Object2LongMap.Entry<K> ok) { throw new UnsupportedOperationException(); }
/*  951: 951 */    public void add(Object2LongMap.Entry<K> ok) { throw new UnsupportedOperationException(); } }
/*  952:     */  
/*  953:     */  private final class MapEntrySet extends AbstractObjectSortedSet<Object2LongMap.Entry<K>> implements Object2LongSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*  954:     */    
/*  955: 955 */    public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> iterator() { return new Object2LongLinkedOpenHashMap.EntryIterator(Object2LongLinkedOpenHashMap.this); }
/*  956:     */    
/*  957: 957 */    public Comparator<? super Object2LongMap.Entry<K>> comparator() { return null; }
/*  958: 958 */    public ObjectSortedSet<Object2LongMap.Entry<K>> subSet(Object2LongMap.Entry<K> fromElement, Object2LongMap.Entry<K> toElement) { throw new UnsupportedOperationException(); }
/*  959: 959 */    public ObjectSortedSet<Object2LongMap.Entry<K>> headSet(Object2LongMap.Entry<K> toElement) { throw new UnsupportedOperationException(); }
/*  960: 960 */    public ObjectSortedSet<Object2LongMap.Entry<K>> tailSet(Object2LongMap.Entry<K> fromElement) { throw new UnsupportedOperationException(); }
/*  961:     */    
/*  962: 962 */    public Object2LongMap.Entry<K> first() { if (Object2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException();
/*  963: 963 */      return new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this, Object2LongLinkedOpenHashMap.this.first);
/*  964:     */    }
/*  965:     */    
/*  966: 966 */    public Object2LongMap.Entry<K> last() { if (Object2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException();
/*  967: 967 */      return new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this, Object2LongLinkedOpenHashMap.this.last);
/*  968:     */    }
/*  969:     */    
/*  970:     */    public boolean contains(Object o) {
/*  971: 971 */      if (!(o instanceof Map.Entry)) return false;
/*  972: 972 */      Map.Entry<K, Long> e = (Map.Entry)o;
/*  973: 973 */      K k = e.getKey();
/*  974:     */      
/*  975: 975 */      int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & Object2LongLinkedOpenHashMap.this.mask;
/*  976:     */      
/*  977: 977 */      while (Object2LongLinkedOpenHashMap.this.used[pos] != 0) {
/*  978: 978 */        if (Object2LongLinkedOpenHashMap.this.key[pos] == null ? k == null : Object2LongLinkedOpenHashMap.this.key[pos].equals(k)) return Object2LongLinkedOpenHashMap.this.value[pos] == ((Long)e.getValue()).longValue();
/*  979: 979 */        pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*  980:     */      }
/*  981: 981 */      return false;
/*  982:     */    }
/*  983:     */    
/*  984:     */    public boolean remove(Object o) {
/*  985: 985 */      if (!(o instanceof Map.Entry)) return false;
/*  986: 986 */      Map.Entry<K, Long> e = (Map.Entry)o;
/*  987: 987 */      K k = e.getKey();
/*  988:     */      
/*  989: 989 */      int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & Object2LongLinkedOpenHashMap.this.mask;
/*  990:     */      
/*  991: 991 */      while (Object2LongLinkedOpenHashMap.this.used[pos] != 0) {
/*  992: 992 */        if (Object2LongLinkedOpenHashMap.this.key[pos] == null ? k == null : Object2LongLinkedOpenHashMap.this.key[pos].equals(k)) {
/*  993: 993 */          Object2LongLinkedOpenHashMap.this.remove(e.getKey());
/*  994: 994 */          return true;
/*  995:     */        }
/*  996: 996 */        pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*  997:     */      }
/*  998: 998 */      return false;
/*  999:     */    }
/* 1000:     */    
/* 1001:1001 */    public int size() { return Object2LongLinkedOpenHashMap.this.size; }
/* 1002:     */    
/* 1003:     */    public void clear() {
/* 1004:1004 */      Object2LongLinkedOpenHashMap.this.clear();
/* 1005:     */    }
/* 1006:     */    
/* 1007:1007 */    public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> iterator(Object2LongMap.Entry<K> from) { return new Object2LongLinkedOpenHashMap.EntryIterator(Object2LongLinkedOpenHashMap.this, from.getKey()); }
/* 1008:     */    
/* 1009:     */    public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> fastIterator() {
/* 1010:1010 */      return new Object2LongLinkedOpenHashMap.FastEntryIterator(Object2LongLinkedOpenHashMap.this);
/* 1011:     */    }
/* 1012:     */    
/* 1013:1013 */    public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> from) { return new Object2LongLinkedOpenHashMap.FastEntryIterator(Object2LongLinkedOpenHashMap.this, from.getKey()); }
/* 1014:     */  }
/* 1015:     */  
/* 1016:     */  public Object2LongSortedMap.FastSortedEntrySet<K> object2LongEntrySet() {
/* 1017:1017 */    if (this.entries == null) this.entries = new MapEntrySet(null);
/* 1018:1018 */    return this.entries;
/* 1019:     */  }
/* 1020:     */  
/* 1023:     */  private final class KeyIterator
/* 1024:     */    extends Object2LongLinkedOpenHashMap<K>.MapIterator
/* 1025:     */    implements ObjectListIterator<K>
/* 1026:     */  {
/* 1027:1027 */    public KeyIterator() { super(k, null); }
/* 1028:1028 */    public K previous() { return Object2LongLinkedOpenHashMap.this.key[previousEntry()]; }
/* 1029:1029 */    public void set(K k) { throw new UnsupportedOperationException(); }
/* 1030:1030 */    public void add(K k) { throw new UnsupportedOperationException(); }
/* 1031:1031 */    public KeyIterator() { super(null); }
/* 1032:1032 */    public K next() { return Object2LongLinkedOpenHashMap.this.key[nextEntry()]; } }
/* 1033:     */  
/* 1034:     */  private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/* 1035:     */    
/* 1036:1036 */    public ObjectListIterator<K> iterator(K from) { return new Object2LongLinkedOpenHashMap.KeyIterator(Object2LongLinkedOpenHashMap.this, from); }
/* 1037:     */    
/* 1038:     */    public ObjectListIterator<K> iterator() {
/* 1039:1039 */      return new Object2LongLinkedOpenHashMap.KeyIterator(Object2LongLinkedOpenHashMap.this);
/* 1040:     */    }
/* 1041:     */    
/* 1042:1042 */    public int size() { return Object2LongLinkedOpenHashMap.this.size; }
/* 1043:     */    
/* 1045:1045 */    public boolean contains(Object k) { return Object2LongLinkedOpenHashMap.this.containsKey(k); }
/* 1046:     */    
/* 1047:     */    public boolean remove(Object k) {
/* 1048:1048 */      int oldSize = Object2LongLinkedOpenHashMap.this.size;
/* 1049:1049 */      Object2LongLinkedOpenHashMap.this.remove(k);
/* 1050:1050 */      return Object2LongLinkedOpenHashMap.this.size != oldSize;
/* 1051:     */    }
/* 1052:     */    
/* 1053:1053 */    public void clear() { Object2LongLinkedOpenHashMap.this.clear(); }
/* 1054:     */    
/* 1055:     */    public K first() {
/* 1056:1056 */      if (Object2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException();
/* 1057:1057 */      return Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.first];
/* 1058:     */    }
/* 1059:     */    
/* 1060:1060 */    public K last() { if (Object2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException();
/* 1061:1061 */      return Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.last]; }
/* 1062:     */    
/* 1063:1063 */    public Comparator<? super K> comparator() { return null; }
/* 1064:1064 */    public final ObjectSortedSet<K> tailSet(K from) { throw new UnsupportedOperationException(); }
/* 1065:1065 */    public final ObjectSortedSet<K> headSet(K to) { throw new UnsupportedOperationException(); }
/* 1066:1066 */    public final ObjectSortedSet<K> subSet(K from, K to) { throw new UnsupportedOperationException(); }
/* 1067:     */  }
/* 1068:     */  
/* 1069:1069 */  public ObjectSortedSet<K> keySet() { if (this.keys == null) this.keys = new KeySet(null);
/* 1070:1070 */    return this.keys;
/* 1071:     */  }
/* 1072:     */  
/* 1075:     */  private final class ValueIterator
/* 1076:     */    extends Object2LongLinkedOpenHashMap.MapIterator
/* 1077:     */    implements LongListIterator
/* 1078:     */  {
/* 1079:1079 */    public long previousLong() { return Object2LongLinkedOpenHashMap.this.value[previousEntry()]; }
/* 1080:1080 */    public Long previous() { return Long.valueOf(Object2LongLinkedOpenHashMap.this.value[previousEntry()]); }
/* 1081:1081 */    public void set(Long ok) { throw new UnsupportedOperationException(); }
/* 1082:1082 */    public void add(Long ok) { throw new UnsupportedOperationException(); }
/* 1083:1083 */    public void set(long v) { throw new UnsupportedOperationException(); }
/* 1084:1084 */    public void add(long v) { throw new UnsupportedOperationException(); }
/* 1085:1085 */    public ValueIterator() { super(null); }
/* 1086:1086 */    public long nextLong() { return Object2LongLinkedOpenHashMap.this.value[nextEntry()]; }
/* 1087:1087 */    public Long next() { return Long.valueOf(Object2LongLinkedOpenHashMap.this.value[nextEntry()]); }
/* 1088:     */  }
/* 1089:     */  
/* 1090:1090 */  public LongCollection values() { if (this.values == null) { this.values = new AbstractLongCollection() {
/* 1091:     */        public LongIterator iterator() {
/* 1092:1092 */          return new Object2LongLinkedOpenHashMap.ValueIterator(Object2LongLinkedOpenHashMap.this);
/* 1093:     */        }
/* 1094:     */        
/* 1095:1095 */        public int size() { return Object2LongLinkedOpenHashMap.this.size; }
/* 1096:     */        
/* 1097:     */        public boolean contains(long v) {
/* 1098:1098 */          return Object2LongLinkedOpenHashMap.this.containsValue(v);
/* 1099:     */        }
/* 1100:     */        
/* 1101:1101 */        public void clear() { Object2LongLinkedOpenHashMap.this.clear(); }
/* 1102:     */      };
/* 1103:     */    }
/* 1104:1104 */    return this.values;
/* 1105:     */  }
/* 1106:     */  
/* 1115:     */  @Deprecated
/* 1116:     */  public boolean rehash()
/* 1117:     */  {
/* 1118:1118 */    return true;
/* 1119:     */  }
/* 1120:     */  
/* 1131:     */  public boolean trim()
/* 1132:     */  {
/* 1133:1133 */    int l = HashCommon.arraySize(this.size, this.f);
/* 1134:1134 */    if (l >= this.n) return true;
/* 1135:     */    try {
/* 1136:1136 */      rehash(l);
/* 1137:     */    } catch (OutOfMemoryError cantDoIt) {
/* 1138:1138 */      return false; }
/* 1139:1139 */    return true;
/* 1140:     */  }
/* 1141:     */  
/* 1158:     */  public boolean trim(int n)
/* 1159:     */  {
/* 1160:1160 */    int l = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
/* 1161:1161 */    if (this.n <= l) return true;
/* 1162:     */    try {
/* 1163:1163 */      rehash(l);
/* 1164:     */    } catch (OutOfMemoryError cantDoIt) {
/* 1165:1165 */      return false; }
/* 1166:1166 */    return true;
/* 1167:     */  }
/* 1168:     */  
/* 1177:     */  protected void rehash(int newN)
/* 1178:     */  {
/* 1179:1179 */    int i = this.first;int prev = -1;int newPrev = -1;
/* 1180:     */    
/* 1181:1181 */    K[] key = this.key;
/* 1182:1182 */    long[] value = this.value;
/* 1183:1183 */    int newMask = newN - 1;
/* 1184:1184 */    K[] newKey = (Object[])new Object[newN];
/* 1185:1185 */    long[] newValue = new long[newN];
/* 1186:1186 */    boolean[] newUsed = new boolean[newN];
/* 1187:1187 */    long[] link = this.link;
/* 1188:1188 */    long[] newLink = new long[newN];
/* 1189:1189 */    this.first = -1;
/* 1190:1190 */    for (int j = this.size; j-- != 0;) {
/* 1191:1191 */      K k = key[i];
/* 1192:1192 */      int pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & newMask;
/* 1193:1193 */      while (newUsed[pos] != 0) pos = pos + 1 & newMask;
/* 1194:1194 */      newUsed[pos] = true;
/* 1195:1195 */      newKey[pos] = k;
/* 1196:1196 */      newValue[pos] = value[i];
/* 1197:1197 */      if (prev != -1) {
/* 1198:1198 */        newLink[newPrev] ^= (newLink[newPrev] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/* 1199:1199 */        newLink[pos] ^= (newLink[pos] ^ (newPrev & 0xFFFFFFFF) << 32) & 0x0;
/* 1200:1200 */        newPrev = pos;
/* 1201:     */      }
/* 1202:     */      else {
/* 1203:1203 */        newPrev = this.first = pos;
/* 1204:     */        
/* 1205:1205 */        newLink[pos] = -1L;
/* 1206:     */      }
/* 1207:1207 */      int t = i;
/* 1208:1208 */      i = (int)link[i];
/* 1209:1209 */      prev = t;
/* 1210:     */    }
/* 1211:1211 */    this.n = newN;
/* 1212:1212 */    this.mask = newMask;
/* 1213:1213 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1214:1214 */    this.key = newKey;
/* 1215:1215 */    this.value = newValue;
/* 1216:1216 */    this.used = newUsed;
/* 1217:1217 */    this.link = newLink;
/* 1218:1218 */    this.last = newPrev;
/* 1219:1219 */    if (newPrev != -1)
/* 1220:     */    {
/* 1221:1221 */      newLink[newPrev] |= 4294967295L;
/* 1222:     */    }
/* 1223:     */  }
/* 1224:     */  
/* 1227:     */  public Object2LongLinkedOpenHashMap<K> clone()
/* 1228:     */  {
/* 1229:     */    Object2LongLinkedOpenHashMap<K> c;
/* 1230:     */    
/* 1232:     */    try
/* 1233:     */    {
/* 1234:1234 */      c = (Object2LongLinkedOpenHashMap)super.clone();
/* 1235:     */    }
/* 1236:     */    catch (CloneNotSupportedException cantHappen) {
/* 1237:1237 */      throw new InternalError();
/* 1238:     */    }
/* 1239:1239 */    c.keys = null;
/* 1240:1240 */    c.values = null;
/* 1241:1241 */    c.entries = null;
/* 1242:1242 */    c.key = ((Object[])this.key.clone());
/* 1243:1243 */    c.value = ((long[])this.value.clone());
/* 1244:1244 */    c.used = ((boolean[])this.used.clone());
/* 1245:1245 */    c.link = ((long[])this.link.clone());
/* 1246:1246 */    return c;
/* 1247:     */  }
/* 1248:     */  
/* 1256:     */  public int hashCode()
/* 1257:     */  {
/* 1258:1258 */    int h = 0;
/* 1259:1259 */    int j = this.size;int i = 0; for (int t = 0; j-- != 0;) {
/* 1260:1260 */      while (this.used[i] == 0) i++;
/* 1261:1261 */      if (this != this.key[i])
/* 1262:1262 */        t = this.key[i] == null ? 0 : this.key[i].hashCode();
/* 1263:1263 */      t ^= HashCommon.long2int(this.value[i]);
/* 1264:1264 */      h += t;
/* 1265:1265 */      i++;
/* 1266:     */    }
/* 1267:1267 */    return h;
/* 1268:     */  }
/* 1269:     */  
/* 1270:1270 */  private void writeObject(ObjectOutputStream s) throws IOException { K[] key = this.key;
/* 1271:1271 */    long[] value = this.value;
/* 1272:1272 */    Object2LongLinkedOpenHashMap<K>.MapIterator i = new MapIterator(null);
/* 1273:1273 */    s.defaultWriteObject();
/* 1274:1274 */    for (int j = this.size; j-- != 0;) {
/* 1275:1275 */      int e = i.nextEntry();
/* 1276:1276 */      s.writeObject(key[e]);
/* 1277:1277 */      s.writeLong(value[e]);
/* 1278:     */    }
/* 1279:     */  }
/* 1280:     */  
/* 1281:     */  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1282:1282 */    s.defaultReadObject();
/* 1283:1283 */    this.n = HashCommon.arraySize(this.size, this.f);
/* 1284:1284 */    this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1285:1285 */    this.mask = (this.n - 1);
/* 1286:1286 */    K[] key = this.key = (Object[])new Object[this.n];
/* 1287:1287 */    long[] value = this.value = new long[this.n];
/* 1288:1288 */    boolean[] used = this.used = new boolean[this.n];
/* 1289:1289 */    long[] link = this.link = new long[this.n];
/* 1290:1290 */    int prev = -1;
/* 1291:1291 */    this.first = (this.last = -1);
/* 1292:     */    
/* 1294:1294 */    int i = this.size; for (int pos = 0; i-- != 0;) {
/* 1295:1295 */      K k = s.readObject();
/* 1296:1296 */      long v = s.readLong();
/* 1297:1297 */      pos = (k == null ? 142593372 : HashCommon.murmurHash3(k.hashCode())) & this.mask;
/* 1298:1298 */      while (used[pos] != 0) pos = pos + 1 & this.mask;
/* 1299:1299 */      used[pos] = true;
/* 1300:1300 */      key[pos] = k;
/* 1301:1301 */      value[pos] = v;
/* 1302:1302 */      if (this.first != -1) {
/* 1303:1303 */        link[prev] ^= (link[prev] ^ pos & 0xFFFFFFFF) & 0xFFFFFFFF;
/* 1304:1304 */        link[pos] ^= (link[pos] ^ (prev & 0xFFFFFFFF) << 32) & 0x0;
/* 1305:1305 */        prev = pos;
/* 1306:     */      }
/* 1307:     */      else {
/* 1308:1308 */        prev = this.first = pos;
/* 1309:     */        
/* 1310:1310 */        link[pos] |= -4294967296L;
/* 1311:     */      }
/* 1312:     */    }
/* 1313:1313 */    this.last = prev;
/* 1314:1314 */    if (prev != -1)
/* 1315:     */    {
/* 1316:1316 */      link[prev] |= 4294967295L;
/* 1317:     */    }
/* 1318:     */  }
/* 1319:     */  
/* 1320:     */  private void checkTable() {}
/* 1321:     */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */