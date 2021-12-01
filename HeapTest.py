import unittest
from components.FiboHeap import FibonacciHeap


class HeapTest(unittest.TestCase):

    def test_instantiation(self):
        dummy_heap = FibonacciHeap()
        self.assertIsNotNone(dummy_heap)  # Test null
        self.assertEqual(dummy_heap.size, 0)  # Test heap size
        self.assertEqual(dummy_heap.get_size(), 0)

    def test_empty(self):
        heap = FibonacciHeap()
        # Check size
        self.assertEqual(heap.size, 0)
        self.assertEqual(heap.get_size(), 0)
        self.assertTrue(heap.is_empty())
        # Check error printout
        self.assertIsNone(heap.get_minimum_node())

        # heap.extract_minimum() this won't work until the cut method is active

    def test_insert(self):
        heap = FibonacciHeap()
        heap.insert_node(5)
        self.assertEqual(heap.size, 1)
        self.assertEqual(heap.get_size(), 1)


if __name__ == '__main__':
    unittest.main()
