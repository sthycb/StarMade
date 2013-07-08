/*   1:    */package it.unimi.dsi.fastutil.chars;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */import java.util.Collection;
/*   5:    */import java.util.List;
/*   6:    */import java.util.Random;
/*   7:    */
/*  57:    */public class CharLists
/*  58:    */{
/*  59:    */  public static CharList shuffle(CharList l, Random random)
/*  60:    */  {
/*  61: 61 */    for (int i = l.size(); i-- != 0;) {
/*  62: 62 */      int p = random.nextInt(i + 1);
/*  63: 63 */      char t = l.getChar(i);
/*  64: 64 */      l.set(i, l.getChar(p));
/*  65: 65 */      l.set(p, t);
/*  66:    */    }
/*  67: 67 */    return l;
/*  68:    */  }
/*  69:    */  
/*  71:    */  public static class EmptyList
/*  72:    */    extends CharCollections.EmptyCollection
/*  73:    */    implements CharList, Serializable, Cloneable
/*  74:    */  {
/*  75:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  76:    */    
/*  77: 77 */    public void add(int index, char k) { throw new UnsupportedOperationException(); }
/*  78: 78 */    public boolean add(char k) { throw new UnsupportedOperationException(); }
/*  79: 79 */    public char removeChar(int i) { throw new UnsupportedOperationException(); }
/*  80: 80 */    public char set(int index, char k) { throw new UnsupportedOperationException(); }
/*  81: 81 */    public int indexOf(char k) { return -1; }
/*  82: 82 */    public int lastIndexOf(char k) { return -1; }
/*  83: 83 */    public boolean addAll(Collection<? extends Character> c) { throw new UnsupportedOperationException(); }
/*  84: 84 */    public boolean addAll(int i, Collection<? extends Character> c) { throw new UnsupportedOperationException(); }
/*  85: 85 */    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
/*  86: 86 */    public Character get(int i) { throw new IndexOutOfBoundsException(); }
/*  87: 87 */    public boolean addAll(CharCollection c) { throw new UnsupportedOperationException(); }
/*  88: 88 */    public boolean addAll(CharList c) { throw new UnsupportedOperationException(); }
/*  89: 89 */    public boolean addAll(int i, CharCollection c) { throw new UnsupportedOperationException(); }
/*  90: 90 */    public boolean addAll(int i, CharList c) { throw new UnsupportedOperationException(); }
/*  91: 91 */    public void add(int index, Character k) { throw new UnsupportedOperationException(); }
/*  92: 92 */    public boolean add(Character k) { throw new UnsupportedOperationException(); }
/*  93: 93 */    public Character set(int index, Character k) { throw new UnsupportedOperationException(); }
/*  94: 94 */    public char getChar(int i) { throw new IndexOutOfBoundsException(); }
/*  95: 95 */    public Character remove(int k) { throw new UnsupportedOperationException(); }
/*  96: 96 */    public int indexOf(Object k) { return -1; }
/*  97: 97 */    public int lastIndexOf(Object k) { return -1; }
/*  98:    */    
/* 101:    */    @Deprecated
/* 102:102 */    public CharIterator charIterator() { return CharIterators.EMPTY_ITERATOR; }
/* 103:    */    
/* 104:104 */    public CharListIterator listIterator() { return CharIterators.EMPTY_ITERATOR; }
/* 105:    */    
/* 106:106 */    public CharListIterator iterator() { return CharIterators.EMPTY_ITERATOR; }
/* 107:    */    
/* 108:108 */    public CharListIterator listIterator(int i) { if (i == 0) return CharIterators.EMPTY_ITERATOR; throw new IndexOutOfBoundsException(String.valueOf(i)); }
/* 109:    */    
/* 110:    */    @Deprecated
/* 111:111 */    public CharListIterator charListIterator() { return listIterator(); }
/* 112:    */    
/* 113:    */    @Deprecated
/* 114:114 */    public CharListIterator charListIterator(int i) { return listIterator(i); }
/* 115:    */    
/* 116:116 */    public CharList subList(int from, int to) { if ((from == 0) && (to == 0)) return this; throw new IndexOutOfBoundsException(); }
/* 117:    */    
/* 118:    */    @Deprecated
/* 119:119 */    public CharList charSubList(int from, int to) { return subList(from, to); }
/* 120:    */    
/* 121:121 */    public void getElements(int from, char[] a, int offset, int length) { if ((from == 0) && (length == 0) && (offset >= 0) && (offset <= a.length)) return; throw new IndexOutOfBoundsException(); }
/* 122:122 */    public void removeElements(int from, int to) { throw new UnsupportedOperationException(); }
/* 123:    */    
/* 124:124 */    public void addElements(int index, char[] a, int offset, int length) { throw new UnsupportedOperationException(); }
/* 125:125 */    public void addElements(int index, char[] a) { throw new UnsupportedOperationException(); }
/* 126:    */    
/* 127:127 */    public void size(int s) { throw new UnsupportedOperationException(); }
/* 128:    */    
/* 129:    */    public int compareTo(List<? extends Character> o) {
/* 130:130 */      if (o == this) return 0;
/* 131:131 */      return o.isEmpty() ? 0 : -1;
/* 132:    */    }
/* 133:    */    
/* 134:134 */    private Object readResolve() { return CharLists.EMPTY_LIST; }
/* 135:135 */    public Object clone() { return CharLists.EMPTY_LIST; }
/* 136:    */  }
/* 137:    */  
/* 146:146 */  public static final EmptyList EMPTY_LIST = new EmptyList();
/* 147:    */  
/* 151:    */  public static class Singleton
/* 152:    */    extends AbstractCharList
/* 153:    */    implements Serializable, Cloneable
/* 154:    */  {
/* 155:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 156:    */    
/* 159:    */    private final char element;
/* 160:    */    
/* 163:163 */    private Singleton(char element) { this.element = element; }
/* 164:    */    
/* 165:    */    public char getChar(int i) {
/* 166:166 */      if (i == 0) return this.element; throw new IndexOutOfBoundsException(); }
/* 167:167 */    public char removeChar(int i) { throw new UnsupportedOperationException(); }
/* 168:168 */    public boolean contains(char k) { return k == this.element; }
/* 169:    */    
/* 170:170 */    public boolean addAll(Collection<? extends Character> c) { throw new UnsupportedOperationException(); }
/* 171:171 */    public boolean addAll(int i, Collection<? extends Character> c) { throw new UnsupportedOperationException(); }
/* 172:172 */    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
/* 173:173 */    public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
/* 174:    */    
/* 176:    */    public char[] toCharArray()
/* 177:    */    {
/* 178:178 */      char[] a = new char[1];
/* 179:179 */      a[0] = this.element;
/* 180:180 */      return a;
/* 181:    */    }
/* 182:    */    
/* 184:184 */    public CharListIterator listIterator() { return CharIterators.singleton(this.element); }
/* 185:    */    
/* 186:186 */    public CharListIterator iterator() { return listIterator(); }
/* 187:    */    
/* 188:    */    public CharListIterator listIterator(int i) {
/* 189:189 */      if ((i > 1) || (i < 0)) throw new IndexOutOfBoundsException();
/* 190:190 */      CharListIterator l = listIterator();
/* 191:191 */      if (i == 1) l.next();
/* 192:192 */      return l;
/* 193:    */    }
/* 194:    */    
/* 195:    */    public CharList subList(int from, int to)
/* 196:    */    {
/* 197:197 */      ensureIndex(from);
/* 198:198 */      ensureIndex(to);
/* 199:199 */      if (from > to) { throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/* 200:    */      }
/* 201:201 */      if ((from != 0) || (to != 1)) return CharLists.EMPTY_LIST;
/* 202:202 */      return this;
/* 203:    */    }
/* 204:    */    
/* 205:205 */    public int size() { return 1; }
/* 206:206 */    public void size(int size) { throw new UnsupportedOperationException(); }
/* 207:207 */    public void clear() { throw new UnsupportedOperationException(); }
/* 208:    */    
/* 209:209 */    public Object clone() { return this; }
/* 210:    */    
/* 212:212 */    public boolean rem(char k) { throw new UnsupportedOperationException(); }
/* 213:213 */    public boolean addAll(CharCollection c) { throw new UnsupportedOperationException(); }
/* 214:214 */    public boolean addAll(int i, CharCollection c) { throw new UnsupportedOperationException(); }
/* 215:    */  }
/* 216:    */  
/* 225:    */  public static CharList singleton(char element)
/* 226:    */  {
/* 227:227 */    return new Singleton(element, null);
/* 228:    */  }
/* 229:    */  
/* 235:    */  public static CharList singleton(Object element)
/* 236:    */  {
/* 237:237 */    return new Singleton(((Character)element).charValue(), null);
/* 238:    */  }
/* 239:    */  
/* 241:    */  public static class SynchronizedList
/* 242:    */    extends CharCollections.SynchronizedCollection
/* 243:    */    implements CharList, Serializable
/* 244:    */  {
/* 245:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 246:    */    
/* 247:    */    protected final CharList list;
/* 248:    */    
/* 249:    */    protected SynchronizedList(CharList l, Object sync)
/* 250:    */    {
/* 251:251 */      super(sync);
/* 252:252 */      this.list = l;
/* 253:    */    }
/* 254:    */    
/* 255:    */    protected SynchronizedList(CharList l) {
/* 256:256 */      super();
/* 257:257 */      this.list = l;
/* 258:    */    }
/* 259:    */    
/* 260:260 */    public char getChar(int i) { synchronized (this.sync) { return this.list.getChar(i); } }
/* 261:261 */    public char set(int i, char k) { synchronized (this.sync) { return this.list.set(i, k); } }
/* 262:262 */    public void add(int i, char k) { synchronized (this.sync) { this.list.add(i, k); } }
/* 263:263 */    public char removeChar(int i) { synchronized (this.sync) { return this.list.removeChar(i); } }
/* 264:    */    
/* 265:265 */    public int indexOf(char k) { synchronized (this.sync) { return this.list.indexOf(k); } }
/* 266:266 */    public int lastIndexOf(char k) { synchronized (this.sync) { return this.list.lastIndexOf(k); } }
/* 267:    */    
/* 268:268 */    public boolean addAll(int index, Collection<? extends Character> c) { synchronized (this.sync) { return this.list.addAll(index, c); } }
/* 269:    */    
/* 270:270 */    public void getElements(int from, char[] a, int offset, int length) { synchronized (this.sync) { this.list.getElements(from, a, offset, length); } }
/* 271:271 */    public void removeElements(int from, int to) { synchronized (this.sync) { this.list.removeElements(from, to); } }
/* 272:272 */    public void addElements(int index, char[] a, int offset, int length) { synchronized (this.sync) { this.list.addElements(index, a, offset, length); } }
/* 273:273 */    public void addElements(int index, char[] a) { synchronized (this.sync) { this.list.addElements(index, a); } }
/* 274:274 */    public void size(int size) { synchronized (this.sync) { this.list.size(size); } }
/* 275:    */    
/* 276:276 */    public CharListIterator iterator() { return this.list.listIterator(); }
/* 277:277 */    public CharListIterator listIterator() { return this.list.listIterator(); }
/* 278:278 */    public CharListIterator listIterator(int i) { return this.list.listIterator(i); }
/* 279:    */    
/* 280:    */    @Deprecated
/* 281:281 */    public CharListIterator charListIterator() { return listIterator(); }
/* 282:    */    
/* 283:    */    @Deprecated
/* 284:284 */    public CharListIterator charListIterator(int i) { return listIterator(i); }
/* 285:    */    
/* 286:286 */    public CharList subList(int from, int to) { synchronized (this.sync) { return CharLists.synchronize(this.list.subList(from, to), this.sync); } }
/* 287:    */    
/* 288:    */    @Deprecated
/* 289:289 */    public CharList charSubList(int from, int to) { return subList(from, to); }
/* 290:    */    
/* 291:291 */    public boolean equals(Object o) { synchronized (this.sync) { return this.collection.equals(o); } }
/* 292:292 */    public int hashCode() { synchronized (this.sync) { return this.collection.hashCode();
/* 293:    */      } }
/* 294:    */    
/* 295:295 */    public int compareTo(List<? extends Character> o) { synchronized (this.sync) { return this.list.compareTo(o);
/* 296:    */      }
/* 297:    */    }
/* 298:    */    
/* 299:299 */    public boolean addAll(int index, CharCollection c) { synchronized (this.sync) { return this.list.addAll(index, c); } }
/* 300:300 */    public boolean addAll(int index, CharList l) { synchronized (this.sync) { return this.list.addAll(index, l); } }
/* 301:301 */    public boolean addAll(CharList l) { synchronized (this.sync) { return this.list.addAll(l); } }
/* 302:    */    
/* 303:303 */    public Character get(int i) { synchronized (this.sync) { return (Character)this.list.get(i); } }
/* 304:304 */    public void add(int i, Character k) { synchronized (this.sync) { this.list.add(i, k); } }
/* 305:305 */    public Character set(int index, Character k) { synchronized (this.sync) { return (Character)this.list.set(index, k); } }
/* 306:306 */    public Character remove(int i) { synchronized (this.sync) { return (Character)this.list.remove(i); } }
/* 307:307 */    public int indexOf(Object o) { synchronized (this.sync) { return this.list.indexOf(o); } }
/* 308:308 */    public int lastIndexOf(Object o) { synchronized (this.sync) { return this.list.lastIndexOf(o);
/* 309:    */      }
/* 310:    */    }
/* 311:    */  }
/* 312:    */  
/* 317:    */  public static CharList synchronize(CharList l)
/* 318:    */  {
/* 319:319 */    return new SynchronizedList(l);
/* 320:    */  }
/* 321:    */  
/* 327:    */  public static CharList synchronize(CharList l, Object sync)
/* 328:    */  {
/* 329:329 */    return new SynchronizedList(l, sync);
/* 330:    */  }
/* 331:    */  
/* 333:    */  public static class UnmodifiableList
/* 334:    */    extends CharCollections.UnmodifiableCollection
/* 335:    */    implements CharList, Serializable
/* 336:    */  {
/* 337:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 338:    */    protected final CharList list;
/* 339:    */    
/* 340:    */    protected UnmodifiableList(CharList l)
/* 341:    */    {
/* 342:342 */      super();
/* 343:343 */      this.list = l;
/* 344:    */    }
/* 345:    */    
/* 346:346 */    public char getChar(int i) { return this.list.getChar(i); }
/* 347:347 */    public char set(int i, char k) { throw new UnsupportedOperationException(); }
/* 348:348 */    public void add(int i, char k) { throw new UnsupportedOperationException(); }
/* 349:349 */    public char removeChar(int i) { throw new UnsupportedOperationException(); }
/* 350:    */    
/* 351:351 */    public int indexOf(char k) { return this.list.indexOf(k); }
/* 352:352 */    public int lastIndexOf(char k) { return this.list.lastIndexOf(k); }
/* 353:    */    
/* 354:354 */    public boolean addAll(int index, Collection<? extends Character> c) { throw new UnsupportedOperationException(); }
/* 355:    */    
/* 356:356 */    public void getElements(int from, char[] a, int offset, int length) { this.list.getElements(from, a, offset, length); }
/* 357:357 */    public void removeElements(int from, int to) { throw new UnsupportedOperationException(); }
/* 358:358 */    public void addElements(int index, char[] a, int offset, int length) { throw new UnsupportedOperationException(); }
/* 359:359 */    public void addElements(int index, char[] a) { throw new UnsupportedOperationException(); }
/* 360:360 */    public void size(int size) { this.list.size(size); }
/* 361:    */    
/* 362:362 */    public CharListIterator iterator() { return listIterator(); }
/* 363:363 */    public CharListIterator listIterator() { return CharIterators.unmodifiable(this.list.listIterator()); }
/* 364:364 */    public CharListIterator listIterator(int i) { return CharIterators.unmodifiable(this.list.listIterator(i)); }
/* 365:    */    
/* 366:    */    @Deprecated
/* 367:367 */    public CharListIterator charListIterator() { return listIterator(); }
/* 368:    */    
/* 369:    */    @Deprecated
/* 370:370 */    public CharListIterator charListIterator(int i) { return listIterator(i); }
/* 371:    */    
/* 372:372 */    public CharList subList(int from, int to) { return CharLists.unmodifiable(this.list.subList(from, to)); }
/* 373:    */    
/* 374:    */    @Deprecated
/* 375:375 */    public CharList charSubList(int from, int to) { return subList(from, to); }
/* 376:    */    
/* 377:377 */    public boolean equals(Object o) { return this.collection.equals(o); }
/* 378:378 */    public int hashCode() { return this.collection.hashCode(); }
/* 379:    */    
/* 380:    */    public int compareTo(List<? extends Character> o) {
/* 381:381 */      return this.list.compareTo(o);
/* 382:    */    }
/* 383:    */    
/* 385:385 */    public boolean addAll(int index, CharCollection c) { throw new UnsupportedOperationException(); }
/* 386:386 */    public boolean addAll(CharList l) { throw new UnsupportedOperationException(); }
/* 387:387 */    public boolean addAll(int index, CharList l) { throw new UnsupportedOperationException(); }
/* 388:388 */    public Character get(int i) { return (Character)this.list.get(i); }
/* 389:389 */    public void add(int i, Character k) { throw new UnsupportedOperationException(); }
/* 390:390 */    public Character set(int index, Character k) { throw new UnsupportedOperationException(); }
/* 391:391 */    public Character remove(int i) { throw new UnsupportedOperationException(); }
/* 392:392 */    public int indexOf(Object o) { return this.list.indexOf(o); }
/* 393:393 */    public int lastIndexOf(Object o) { return this.list.lastIndexOf(o); }
/* 394:    */  }
/* 395:    */  
/* 402:    */  public static CharList unmodifiable(CharList l)
/* 403:    */  {
/* 404:404 */    return new UnmodifiableList(l);
/* 405:    */  }
/* 406:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.chars.CharLists
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */