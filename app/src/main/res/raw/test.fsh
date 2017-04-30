#ifdef GL_SL
precision mediump float;
#endif

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;



// Created by mmichal

float overlay( float s, float d )
{
	return (d < 0.5) ? 2.0 * s * d : 1.0 - 2.0 * (1.0 - s) * (1.0 - d);
}

vec3 overlay( vec3 s, vec3 d )
{
	vec3 c;
	c.x = overlay(s.x,d.x);
	c.y = overlay(s.y,d.y);
	c.z = overlay(s.z,d.z);
	return c;
}

vec3 color(vec3 d ){

	//Black & White Matrix
	vec3 c_r = vec3(1.0, 0.0, 0.0);
	vec3 c_g = vec3(0.0, 1.0, 0.0);
	vec3 c_b = vec3(-1.0, 3.0, -1.0);

	d = vec3( dot(d.rgb,c_r), dot(d.rgb,c_g), dot(d.rgb,c_b) );

	return d;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    vec2 uv = fragCoord.xy / iResolution.xy;
	vec3 s = texture2D( iChannel0, uv ).rgb;


	// destination texture (lower layer)
	vec3 d = texture2D( iChannel0, uv ).rgb;
    vec3 cold = color(d);

	vec3 c = overlay(cold, d);

	fragColor = vec4(c,1.0);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
