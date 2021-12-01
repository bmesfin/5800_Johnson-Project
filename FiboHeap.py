def greater(x: int, y: int) -> bool:
    """
    Utility/helper function to check if u.key > v.key.

    :param x: value to be compared
    :param y: value to be compared
    :return: boolean
    """
    return compare(x, y) > 0


def compare(x: int, y: int) -> int:
    """
    Utility/helper function for comparison operations.

    :param x: u.key to be compared
    :param y: v.key to be compared
    :return: -1, 0, 1
    """
    if x == y:
        return 0
    elif x < y:
        return -1
    else:
        return 1


def insert(node: object, head: object) -> object:
    """
    Inserts a node into a circular list.

    :param head: current head node
    :param node: node object
    :return: returns a new head node
    """
    if head is None:
        node.previous = node
        node.next = node
    else:
        head.previous.next = node
        node.next = head
        node.previous = head.previous
        head.previous = node
    return node


def cut(node: object, head: object) -> object:
    if node.next == node:
        node.next = None
        node.previous = None
        return None
    else:
        node.next.previous = node.previous
        node.previous.next = node.next
        result = node.next
        node.next = None
        node.previous = None
        if head == node:
            return result
        else:
            return head


class FibonacciHeap:
    """
    Represents a Fibonacci heap which is a data structure used for
    a priority queue, which consists of a collection of heap-ordered trees.
    """

    class Node:
        """
        Represents a graph node from CLRS, Introduction to Algorithms, pg. 505
        """

        def __init__(self):
            self.key = 0  # Represents the node's value
            self.order = 0  # Order of the tree rooted by this node
            self.previous = None
            self.next = None
            self.child = None
            self.mark = False

    def __init__(self) -> None:
        self.head = None
        self.minimum_node = None
        self.size = 0
        self.table = {}
        self.key_list = []

    def is_empty(self) -> bool:
        """
        Checks if the heap is empty.

        :return: boolean
        """
        return self.size == 0

    def get_size(self) -> int:
        """
        Getter method for size.

        :return: size attribute
        """
        return self.size

    def insert_node(self, key: int, value=None) -> None:
        """
        Inserts a node into the heap.

        :param value: placeholder for a node's data
        :param key: node's key/identifier
        """
        new_node = self.Node()
        new_node.key = key
        self.size += 1
        self.head = insert(new_node, self.head)
        if self.minimum_node is None or new_node.key < self.minimum_node.key:
            self.minimum_node = self.head
        else:
            if greater(self.minimum_node.key, key):
                self.minimum_node = self.head

    def get_minimum_node(self):
        """
        Getter method: retrieves the minimum node in the heap.
        :return:
        """
        if self.is_empty():
            print("Priority Queue is empty")
        return self.minimum_node

    def extract_minimum(self):
        if self.is_empty():
            print("Priority Queue is empty")
        self.head = cut(self.minimum_node, self.head)
        child_node = self.minimum_node.child

        if child_node is not None:
            self.head = self.merge(self.head, child_node)
            self.minimum_node.child = None
        self.size -= 1
        if not self.is_empty():
            self.consolidate()
        else:
            self.minimum_node = None

    def merge(self, u: object, v: object) -> object:
        """
        Merges two root lists.

        :param u: node object
        :param v: node object
        :return: node object
        """
        if u is None:
            return v
        if v is None:
            return u
        u.previous.next = v.next
        v.next.previous = u.previous
        u.previous = v
        v.previous = u
        return u

    def consolidate(self):
        return

    def union(self, other: object) -> object:
        self.head = self.merge(self.head, other.head)
        if greater(self.minimum_node.key, other.minimum_node.key):
            self.minimum_node = other.minimum_node

        self.size += other.size
        return self
