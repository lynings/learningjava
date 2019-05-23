### SimpleHashMap

一个精简版的 HashMap 实现

### 预设计
```java
public class SimpleHashMap<K, V> {
    public V put(K key, V value);   
    public V get(K key);   
    public V remove(K key); 
    public boolean containsKey(K key); 
    public int size();
    public Iterator<V> values();
    public void forEach(Consumer<? super K> action);
}
```

### Tasking
- [x] 无参构建 SimpleHashMap 
- [ ] 构造函数初始化 initial capacity 
- [ ] 构造函数初始化 initial capacity 和 load factor 
- [x] initial capacity 默认使用 16
- [x] load factor 默认使用 0.75f
- [x] 初始化的 resize 门槛为 initial capacity * load factor  
- [x] 再次 resize 门槛为 threshold = threshold << 1  
- [ ] 增加 put 接口
    - [x] 计算 hash 值
    - [x] 增加 hash table 用于保存数据节点.
    - [x] 如果 hash table 的容量为 0 或者 hash table 的容量超过门槛，则设置新的 resize 门槛，并扩容和 rehash。
    - [x] hash table 的下标为 hash & (capacity -1)
    - [x] 扩容时需要把旧的 hash table 的数据转移到新的 hash table
    - [x] 转移数据到新的 hash table 之前需要 rehash，rehash = entry.hash & (new_capacity -1)
    - [x] 如果 hash 冲突，使用链表存储
    - [ ] 如果同一个 hash 冲突超过 8 次，使用红黑树存储
- [ ] 增加 size 接口
    - [x] 增加全局的 size 成员变量.
    - [x] put 接口调用成功，则 size += 1.
    - [x] remove 接口调用成功，则 size -= 1.
    - [x] 考虑链表
    - [ ] 考虑红黑树
- [ ] 增加 containsKey 接口
    - [x] 通过 key 计算 hash    
    - [x] 通过 hash 计算 index
    - [x] 通过 index 检索 key，检索到return true,否则 return false，
    - [x] 考虑 hash table 为 null.
    - [x] 考虑链表
    - [ ] 考虑红黑树
- [ ] 增加 get 接口
    - [x] 通过 key 计算 hash    
    - [x] 通过 hash 计算 index
    - [x] 通过 index 检索 bucket
    - [x] 如果 bucket 存在多个数据节点，则需要判断 key 的值和引用是否相等.
    - [x] 如果相等返回对应的 value，否则返回 null.
    - [x] 考虑链表
    - [ ] 考虑红黑树
- [ ] 增加 remove 接口
    - [x] 通过 key 计算 hash    
    - [x] 通过 hash 计算 index
    - [x] 通过 index 检索 bucket
    - [x] 如果相等则将对应的 bucket 置 null，并返回对应的 value，否则返回 null，
    - [x] 考虑链表
    - [ ] 考虑红黑树
- [ ] 增加 values 接口
    - [x] 每次 put 成功时保存 list 中到
    - [x] 每次 put 替换成功时，需要替换 list 中对应的 value
    - [x] 每次 remove 成功时从 list 中到删除
    - [x] 考虑链表
    - [ ] 考虑红黑树
-  [ ] 增加 forEach 接口
    - [x] 遍历 hash table
    - [x] 如果存在 bucket，则通过 action.apply(key) 
    - [x] 考虑链表
    - [ ] 考虑红黑树
- [ ] 增加 fail-fast
    - [ ] 增加 modCount 成员变量用于统计变更次数 
    - [ ] 迭代前后需要验证 modCount 前后是否一致 
    - [ ] 如果 modCount 前后是否一致需要抛出 ConcurrentModificationException. 
- [ ] 增加 rb tree 保存 hash 冲突超过 8 次的数据节点.
    - [ ] 生成红黑树
        - [ ] root 必须为黑色  
        - [ ] 节点 > 左孩子节点 且 节点 < 有孩子节点
        - [ ] 叶子节点为 null  
        - [ ] 黑色节点的子节点必须会红色  
        - [ ] 红色节点的子节点必须都是黑色  
