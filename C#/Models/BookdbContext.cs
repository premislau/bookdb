using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace bookdb.Models;

public partial class BookdbContext : DbContext
{
    public BookdbContext()
    {
    }

    public BookdbContext(DbContextOptions<BookdbContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Author> Authors { get; set; }

    public virtual DbSet<Book> Books { get; set; }


    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Author>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("authors_pkey");

            entity.ToTable("authors", "bookdb");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.FirstName)
                .HasMaxLength(128)
                .HasColumnName("first_name");
            entity.Property(e => e.LastName)
                .HasMaxLength(128)
                .HasColumnName("last_name");
            entity.HasMany(d => d.Books).WithMany(p => p.Authors)
                .UsingEntity<Dictionary<string, object>>(
                    "Authorship",
                    r => r.HasOne<Book>().WithMany()
                        .HasForeignKey("BookId")
                        .HasConstraintName("fk_book"),
                    l => l.HasOne<Author>().WithMany()
                        .HasForeignKey("AuthorId")
                        .HasConstraintName("fk_author"),
                    j =>
                    {
                        j.HasKey("BookId", "AuthorId").HasName("authorships_pkey");
                        j.ToTable("authorships", "bookdb");
                        j.IndexerProperty<Guid>("BookId").HasColumnName("book_id");
                        j.IndexerProperty<Guid>("AuthorId").HasColumnName("author_id");
                    });
        });

        modelBuilder.Entity<Book>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("books_pkey");

            entity.ToTable("books", "bookdb");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.PublicationDate).HasColumnName("publication_date");
            entity.Property(e => e.Title).HasColumnName("title");

            entity.HasMany(d => d.Authors).WithMany(p => p.Books)
                .UsingEntity<Dictionary<string, object>>(
                    "Authorship",
                    r => r.HasOne<Author>().WithMany()
                        .HasForeignKey("AuthorId")
                        .HasConstraintName("fk_author"),
                    l => l.HasOne<Book>().WithMany()
                        .HasForeignKey("BookId")
                        .HasConstraintName("fk_book"),
                    j =>
                    {
                        j.HasKey("BookId", "AuthorId").HasName("authorships_pkey");
                        j.ToTable("authorships", "bookdb");
                        j.IndexerProperty<Guid>("BookId").HasColumnName("book_id");
                        j.IndexerProperty<Guid>("AuthorId").HasColumnName("author_id");
                    });
        });
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
