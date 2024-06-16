import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.Callable;

class queueNode<T>{
    T data;
    queueNode<T> next;
    int num=0;
    public static int count=-1;

    public queueNode() {
        this.num=count++;
    }
}
class queueLinkedList<T>{
    int size=0;
    queueNode<T> head=null;
    queueNode<T> tail=null;
    public boolean isEmpty(){
        return tail==null && head==null;
    }
    //enqueue
    public void add(T data){
        queueNode<T> newNode =new queueNode<T>();
        newNode.data=data;
        newNode.next=null;
        if(head==null){
            head=tail=newNode;
            return;
        }

        tail.next=newNode;
        tail=newNode;
        size++;

    }
    public void remove(){
        if(isEmpty()){
            System.out.println("Empty queue");
            throw new IllegalArgumentException();
        }
        // T front =head.data;
        if(head==tail){
            tail=null;
        }
        head=head.next;
        // return front;
    }
    public T peek(){
        //O(n)
        if(isEmpty()){
            System.out.println("Queue empty");
            throw new IllegalArgumentException();

        }else {
            return head.data;
        }
    }
    void deleteNode(int position) {

        if (head == null) {
            return;
        }
        queueNode<T> temp = head;
        if (position == 0) {
            head = temp.next;
            return;
        }

        for (int i = 0; temp != null && i < position - 1; i++) {
            temp=temp.next;
        }

        if (temp == null || temp.next == null) {
            return;
        }
        queueNode<T> next = temp.next.next;

        temp.next= next;
    }

}
class DoublyNode<T>{
    T data;
    int num;
    static int count=1;

    public DoublyNode() {
        this.num=getCount();
    }

    private static int getCount() {
        return count++;
    }

    DoublyNode<T> next;
    DoublyNode<T> prev;
}
class DoublyLinkedList<T>{
    DoublyNode<T> head=null,tail=null;
    public void add(T data){
        DoublyNode<T> newNode=new DoublyNode<T>();
        newNode.data=data;
        newNode.next=null;
        newNode.prev=null;
        //No node exist
        if(head==null){
            head=newNode;
            tail=newNode;
            head.prev=null;
            tail.next=null;
        }else {
            tail.next=newNode;
            newNode.prev=tail;
            tail=newNode;
            tail.next=null;
        }
    }
    public void insertAtAny(T data,int pos){
        DoublyNode<T> newNode=new DoublyNode<T>();
        newNode.data=data;
        newNode.next=null;
        newNode.prev=null;
        DoublyNode<T> curr=head;
        int i=1;
        while (i<pos-1){
            curr=curr.next;
            i++;
        }
        newNode.prev=curr;
        newNode.next=curr.next;
        curr.next.prev=newNode;
        curr.next=newNode;

    }
    public void del(int position){
// if want to delete tail
//       tail= tail.prev;
        // tail.next=null;
        if(position==1){
            head=head.next;
            head.prev=null;

        }else {
            DoublyNode<T> curr=head;
            int i=1;
            while (i<position-1){
                curr=curr.next;
                i++;
            }
            System.out.println(curr.data);
            DoublyNode<T> temp=curr.next;
            curr.next=temp.next;
            temp.next=curr;

            // System.out.println(curr.next.data);
        }

    }
    public void reverse(){
        DoublyNode<T> tempNode=null,prevNode=null;
        DoublyNode<T> currNode=head;

        while (currNode!=null){
            tempNode=currNode.next;
            currNode.next=prevNode;
            currNode.prev=tempNode;
            prevNode=currNode;
            currNode=tempNode;
            //currNode=currNode.next;
        }
        head=prevNode;

    }
    static class ReadInput implements Callable<String> {
        public String call() throws IOException {
            System.out.println("Enter some thing");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (br.ready() == false) {
                    Thread.sleep(250);
                }
                String input = br.readLine();
                return input;

            } catch (InterruptedException e) {
                return null;
            }
        }
    }
    public void sort(){
        DoublyNode<T>curr,index;
        for(curr=head;curr!=null;curr=curr.next){
            for(index=curr.next;index!=null;index=index.next){
                if(curr.num>index.num){
                    T temp=curr.data;
                    curr.data=index.data;
                    index.data=temp;

                    int tempNum=curr.num;
                    curr.num=index.num;;
                    index.num=tempNum;
                }
            }
        }
    }
    private Random random=new Random();
    public void assignRandomNum(){
        DoublyNode<T>curr=head;
        while (curr!=null){
            curr.num=random.nextInt(0,DoublyNode.count);
            curr=curr.next;
        }
    }

    public void shuffle(){
        assignRandomNum();
        sort();
    }


    public void display(){
        DoublyNode<T> curr=head;
        while(curr!=null){
            System.out.print("  "+curr.data);
            curr=curr.next;
        }
    }
}

public class Classes{
    public static void main(String[] args) {
        queueLinkedList<Integer>q=new queueLinkedList<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        q.deleteNode(1);
    }
}
