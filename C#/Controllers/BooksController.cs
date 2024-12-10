using System;
using bookdb.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace bookdb.Controllers;

[ApiController]
[Route("books")]
public class BookController: ControllerBase
{
	private readonly BookdbContext _context;
	public BookController(BookdbContext context)
	{
		_context = context;
	}
	
	[HttpGet]
	public async Task<ActionResult<IEnumerable<Book>>> GetAll(){
		return await _context.Books.ToListAsync();
	}

	[HttpPost]
	public async Task<ActionResult<Book>> Create(Book book){
		_context.Books.Add(book);
		await _context.SaveChangesAsync();
		return Ok(book);
	}

	[HttpDelete("{id}")]
	public async Task<IActionResult> DeleteMyEntity(int id){
		var book = await _context.Books.FindAsync(id);
		if (book == null)
		{
			return NotFound();
		}

		_context.Books.Remove(book);
		await _context.SaveChangesAsync();

		return NoContent();
	}

		[HttpGet("{id}/authors")]
	public async Task<ActionResult<IEnumerable<Book>>> GetBooksByAuthor(Guid id){
		var book = await _context.Books
			.Include(b => b.Authors)
			.FirstOrDefaultAsync(b => b.Id == id);

		if (book == null)
		{
			return NotFound();
		}

		return Ok(book.Authors);
	}

	[HttpPost("{bookId}/authors")]
	public async Task<ActionResult> AddMultipleBooks(Guid bId, List<Guid> authorIds){
		var book = await _context.Books
			.Include(a => a.Authors)
			.FirstOrDefaultAsync(a => a.Id == bId);
		
		if (book == null)
		{
			return NotFound(new { Message = "Book not found" });
		}
		List<Author> AuthorsToAdd = new List<Author>(authorIds.Count);
		foreach(Guid authorId in authorIds){
			Author? author =  await _context.Authors.FindAsync(authorId);			
			if(null == author){
				return BadRequest(new {Message=$"There is no book with id {authorId} in the database"});
			}
			AuthorsToAdd.Add(author);
		}

		//books are added only if all bookIds are present in the database
		foreach (var author in AuthorsToAdd)
		{
			book.Authors.Add(author);
		}
		await _context.SaveChangesAsync();

		return Ok(book);
	}
}
