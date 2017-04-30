#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
uniform sampler2D           iChannel1;
varying vec2                texCoord;

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec4 tex1Color = texture2D(iChannel0, fragCoord.xy / iResolution.xy);
	vec2 pos = fragCoord.xy;
	vec2 uv2 = vec2( fragCoord.xy / iResolution.xy );
	vec4 sound = texture2D( iChannel0, uv2 );

    const int kSize = 5;
    vec3 avg = vec3(0.0);
    for (int i=-kSize; i <= kSize; ++i) {
        for (int j = -kSize; j <= kSize; ++j) {
            avg = avg + texture2D(iChannel0, (fragCoord.xy + vec2(float(i), float(j)))/iResolution.xy).xyz;
        }
    }
    int area = (2*kSize + 1) * (2*kSize + 1);
    avg = avg.xyz/vec3(area); //interestingly divider is not x^2 but x^3. since the area of a box is x^2..

	sound = vec4(avg, tex1Color.a);


	pos.x = pos.x + 150.0 * sound.r;
	pos.y = pos.y + 150.0 * sound.b;
	vec2 uv = pos / iResolution.xy;

	vec4 col = texture2D( iChannel1, uv );

	col.a += 1.0 - sin( col.x - col.y + iGlobalTime * 0.1 );

	fragColor =  col * sound.r;
}

void main() {
	mainImage(gl_FragColor, texCoord * iResolution.xy);
}