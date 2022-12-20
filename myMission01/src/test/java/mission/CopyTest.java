package mission;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Objects;

/**
 * Object 와 Collection을 복사하는 여러 방법을 테스트
 * 
 * - 깊은 복사 vs 얇은 복사
 * - 해시코드 동일성
 * - 원본 컬렉션 변경에 따른 영향
 *
 * [참고 서적 및 블로그]
 * 
 * Cloneable 인터페이스와 clone 메소드 
 * https://catsbi.oopy.io/16109e87-3c7e-4c6e-9816-c86e6b343cdd
 * 
 * Java 깊은 복사(Deep Copy)와 얕은 복사(Shallow Copy)
 * https://zzang9ha.tistory.com/372
 * 
 * [Java] Collection 을 복사하는 여러 방법
 * https://hudi.blog/ways-to-copy-collection/
 * 
 * 이펙티브 자바 ITEM 41 
 */
public class CopyTest {

	@Test
	void objectShallowCopy () {
		//given
		Fruit original = new Fruit("apple");
		
		//when
		Fruit copy = original;
		copy.setName("banana");
		
		//then
		assertThat(original).isSameAs(copy);
		assertThat(original.getName()).isEqualTo(copy.getName());
	}
	
	// clone
	@Test
	void objectShallowCopyByClone () throws CloneNotSupportedException {
		// 필드 객체가 같음.
		
		//given
		Fruit original = new Fruit("apple");
		
		//when
		Fruit copy = (Fruit) original.clone();
		
		//then
		assertThat(original).isNotSameAs(copy);
		assertThat(copy.getName()).isSameAs(original.getName());
	}
	
	@Test
	void objectDeepCopyByClone () throws CloneNotSupportedException {
		//given
		Fruit2 original = new Fruit2("apple");
		
		//when
		Fruit2 copy = (Fruit2) original.clone();
		
		//then
		assertThat(original).isNotSameAs(copy);
		assertThat(original.getName()).isEqualTo(copy.getName()); // 동등성 o ,객체의 값이 같다.
		assertThat(original.getName()).isNotSameAs(copy.getName()); // 동일성 x, 서로 다른 주소를 가짐.
	}
	
	@Test
	void objectDeepCobyByConstructor () {
		//given
		Fruit original = new Fruit("apple");
		
		//when
		Fruit copy = new Fruit(original);
		// copy.setName("banana");
		
		//then
		assertThat(original).isNotSameAs(copy);
		assertThat(original.getName()).isEqualTo(copy.getName()); // 동등성 o ,객체의 값이 같다.
		assertThat(original.getName()).isNotSameAs(copy.getName()); // 동일성 x, 서로 다른 주소를 가짐.
	}
	
	class Fruit implements Cloneable{
		private String name;
		
		public Fruit(String name) {
			this.name = name;
		}
		
		public Fruit(Fruit fruit) {
			// jvm은 내부적으로 메모리를 아끼기 위해 리터럴로 값을 선언하면 JVM 상수풀에서 리터럴을 해싱한 hashcode를 찾는다.
			// 없으면 상수 풀에 리터럴을 해싱한 값 추가한다.
			// String str1 = "hello";
			// String str2 = "hello"; 
			// str1 == str2
			// 여기서는 깊은 복사가 되지 않는다는 것을 테스트하기 위해 new 키워드를 사용했다.
			this.name = new String(fruit.name);
		}
		

		public void setName(String name) {
			this.name = name;
		}


		public String getName() {
			return name;
		}

		// shallow copy
		@Override
	    public Object clone() throws CloneNotSupportedException {
	        return super.clone();    
	    }
	}
	
	class Fruit2 implements Cloneable{
		private String name;
		
		public Fruit2(String name) {
			this.name = name;
		}
		
		public Fruit2(Fruit fruit) {
			this.name = fruit.name;
		}
		

		public void setName(String name) {
			this.name = name;
		}


		public String getName() {
			return name;
		}

		// deep copy
		@Override
		public Object clone() throws CloneNotSupportedException {
			Fruit2 fruit2 = (Fruit2) super.clone();
			fruit2.setName(new String(this.name));
			return fruit2;
		}
	}
}
