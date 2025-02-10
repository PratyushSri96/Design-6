//Design Phone directory
class PhoneDirectory {
    HashSet<Integer> set;
    Queue<Integer> q;

    public PhoneDirectory(int maxNumbers) {
        this.set = new HashSet<>();
        this.q = new LinkedList<>();
        for(int i = 0 ; i<maxNumbers ; i++){
            q.add(i);
            set.add(i);
        }
    }
    
    public int get() {
        //the function is to give the next avaibale number
        if(q.isEmpty()) return -1;
        int re = q.poll();
        set.remove(re);
        return re;
    }
    
    public boolean check(int number) {
        return set.contains(number);
    }
    
    public void release(int number) {
        if(!set.contains(number)){
            set.add(number);
            q.add(number);
        }
        
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */

//AutoComplete
class AutocompleteSystem {
    HashMap<String,Integer> map;
    private String search;
    PriorityQueue<String> pq;
    
    class TrieNode{
        TrieNode[] children;
        List<String> startsWith;
        public TrieNode(){
            this.children = new TrieNode[256];
            this.startsWith = new ArrayList();
        }
    }
    private TrieNode root;

    private void insert(String word){
        TrieNode curr = root;
         for(int i = 0 ; i<word.length();i++){
            char c = word.charAt(i);
            if(curr.children[c - ' ']==null){
                curr.children[c - ' '] = new TrieNode();
            }
            curr = curr.children[c -' '];
            curr.startsWith.add(word);
         }
    }

  

    private List<String> searchPrefix(String prefix){
        TrieNode curr = root;
         for(int i = 0 ; i<prefix.length();i++){
            char c = prefix.charAt(i);
            if(curr.children[c - ' ']==null){
                return new ArrayList<>();
            }
            curr = curr.children[c -' '];
         }
         return curr.startsWith;
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.map = new HashMap();
        this.search = "";
        this.root = new TrieNode();
        this.pq = new PriorityQueue<>((String a,String b) -> {
        if(map.get(a) == map.get(b)){
            return b.compareTo(a);
        }
        return map.get(a) - map.get(b);
    });
        for(int i = 0 ; i<sentences.length;i++){
            String sentence = sentences[i];
            if(!map.containsKey(sentence)){
                insert(sentence);
            }
            int time = times[i];
            map.put(sentence,map.getOrDefault(sentence, 0) + time);
        }
    }
    
    public List<String> input(char c) {
        List<String> result = new ArrayList<>();
        if(c == '#'){
            if(!map.containsKey(search)){
                insert(search);
            }
            map.put(search,map.getOrDefault(search, 0) + 1);
            this.search = "";
            return new ArrayList<>();
        }
        List<String> startsWith = searchPrefix(search);
        this.search+=c;
        for(String sentence : startsWith){
            
                pq.add(sentence);
                if(pq.size()>3){
                    pq.poll();
                }
            
        }
        while(!pq.isEmpty()){
            result.add(0,pq.poll());
        }

        return result;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */