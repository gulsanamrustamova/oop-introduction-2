/**
 * OOP Advanced: comparing OOP vs procedural for a Library system.
 * Shows how OOP scales better for complex systems.
 */
import java.util.*;

public class OopIntroductionAdvanced {

    // ─── Without OOP: parallel arrays (messy, error-prone) ─────────
    static void proceduralLibrary() {
        String[] titles   = {"Java 21","Clean Code","Design Patterns"};
        String[] authors  = {"Gosling", "Martin",    "GoF"};
        boolean[] checkedOut = {false,  true,         false};

        System.out.println("=== Procedural Library (parallel arrays) ===");
        for (int i=0; i<titles.length; i++)
            System.out.printf("%-20s by %-10s → %s%n",
                titles[i], authors[i], checkedOut[i] ? "Checked Out" : "Available");
    }

    // ─── With OOP: clean, maintainable, extensible ──────────────────
    static class Book {
        private final String title, author, isbn;
        private boolean checkedOut;
        private String borrower;

        Book(String title, String author, String isbn) {
            this.title  = title; this.author = author; this.isbn = isbn;
        }

        boolean checkout(String borrowerName) {
            if (checkedOut) return false;
            checkedOut = true; borrower = borrowerName; return true;
        }

        boolean returnBook() {
            if (!checkedOut) return false;
            checkedOut = false; borrower = null; return true;
        }

        String status() { return checkedOut ? "Out (" + borrower + ")" : "Available"; }

        @Override public String toString() {
            return String.format("%-25s by %-15s [%s] → %s", title, author, isbn, status());
        }
    }

    static class Library {
        private final String name;
        private final List<Book> books = new ArrayList<>();

        Library(String name) { this.name = name; }

        void addBook(Book b)   { books.add(b); }
        Optional<Book> find(String title) {
            return books.stream().filter(b -> b.title.equalsIgnoreCase(title)).findFirst();
        }
        long availableCount() { return books.stream().filter(b -> !b.checkedOut).count(); }
        void printCatalog()   { books.forEach(b -> System.out.println("  " + b)); }
    }

    public static void main(String[] args) {
        proceduralLibrary();

        System.out.println("\n=== OOP Library System ===");
        Library lib = new Library("City Library");
        lib.addBook(new Book("Java 21",          "James Gosling",  "ISBN-001"));
        lib.addBook(new Book("Clean Code",        "Robert Martin",  "ISBN-002"));
        lib.addBook(new Book("Design Patterns",   "GoF",            "ISBN-003"));
        lib.addBook(new Book("Effective Java",    "Joshua Bloch",   "ISBN-004"));

        lib.find("Clean Code").ifPresent(b -> System.out.println("Checkout: " + b.checkout("Alice")));
        lib.find("Java 21").ifPresent(b -> System.out.println("Checkout: " + b.checkout("Bob")));

        System.out.println("\nCatalog:");
        lib.printCatalog();
        System.out.println("Available: " + lib.availableCount() + "/" + 4);

        lib.find("Clean Code").ifPresent(b -> { b.returnBook(); System.out.println("\nReturned: " + b); });
    }
}
