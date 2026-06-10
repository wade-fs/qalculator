package com.jherkenhoff.qalculate

import com.jherkenhoff.qalculate.model.MutableTrie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TrieTest {
    @Test
    fun testEmptyTrie() {
        val trie = MutableTrie<String>()
        val results = trie.search("test")
        assertTrue(results.isEmpty())
    }

    @Test
    fun testTrieInsertAndSearch() {
        val trie = MutableTrie<String>()
        trie.insert("test", "value1")
        trie.insert("tea", "value2")
        trie.insert("toast", "value3")

        val results = trie.search("te")
        assertEquals(2, results.size)
        val values = results.map { it.value }
        assertTrue(values.contains("value1"))
        assertTrue(values.contains("value2"))
    }

    @Test
    fun testTrieExactSearch() {
        val trie = MutableTrie<String>()
        trie.insert("test", "value1")
        trie.insert("testing", "value2")
        trie.insert("tester", "value3")

        val results = trie.search("test")
        assertEquals(3, results.size)
        val values = results.map { it.value }
        assertTrue(values.contains("value1"))
        assertTrue(values.contains("value2"))
        assertTrue(values.contains("value3"))
    }
}
